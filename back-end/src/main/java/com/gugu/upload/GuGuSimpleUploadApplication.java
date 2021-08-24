package com.gugu.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * The type Gu gu simple upload application.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GuGuSimpleUploadApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GuGuSimpleUploadApplication.class, args);
    }

}
