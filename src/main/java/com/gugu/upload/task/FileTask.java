package com.gugu.upload.task;

import com.gugu.upload.common.entity.FileInfo;
import com.gugu.upload.config.ApplicationConfig;
import com.gugu.upload.service.IFileService;
import com.gugu.upload.utils.StatusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 维护文件状态
 *
 * @author minmin
 * @date 2021 /08/14
 * @since 1.0
 */
@Slf4j
@Component
public class FileTask {
    @Resource
    private IFileService fileService;

    @Resource
    private ApplicationConfig applicationConfig;

    /**
     * Check document validity.
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void checkDocumentValidity(){
        fileService.list().forEach(f -> {
            Path path = Paths.get(f.getFilePath());
            if (Files.notExists(path)) {
                log.info("That a file record in the database does not exist on the disk. File info : {}", f);
                flagFileInvalid(f);
            }
        });
    }

    private void flagFileInvalid(FileInfo fileInfo){
        fileInfo.setStatus(StatusUtil.Status.FAIL);
        fileService.updateById(fileInfo);
    }

    /**
     * Clean up records.
     */
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void cleanUpRecords(){
        fileService.list().forEach(f -> {
            if (StatusUtil.Status.FAIL == f.getStatusDescription()){
                log.info("The updated value is logic delete : {}", f);
                fileService.removeById(f.getId());
            }
        });
    }

    /**
     * Clean file.
     *
     * @throws IOException the io exception
     */
    @Scheduled(cron = "0 0/20 * * * ? ")
    public void cleanFile() throws IOException {
        Map<String, String> dataMap = fileService.list().stream().collect(Collectors.toMap(FileInfo::getFilePath, FileInfo::getFilePath));
        Files.walkFileTree(Paths.get(applicationConfig.getTmpDir()), new CleanUpIrrelevantFiles(dataMap));
    }

    private static class CleanUpIrrelevantFiles extends SimpleFileVisitor<Path>{
        private final Map<String, String> dataMap;

        /**
         * Instantiates a new Clean up irrelevant files.
         *
         * @param dataMap the data map
         */
        public CleanUpIrrelevantFiles(Map<String, String> dataMap) {
            this.dataMap = dataMap;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            String path = dataMap.get(dir.toString());
            if (StringUtils.isEmpty(path)){
                log.info("Irrelevant files will be deleted : {}", dir);
                try {
                    Files.delete(dir);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("Failed to delete irrelevant files");
                }
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
