package com.kef.org.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.kef.org.rest.config.FileStorageConfig;




@SpringBootApplication(scanBasePackages = {"com.kef.org"})
@EnableConfigurationProperties({
    FileStorageConfig.class
})
public class KEFVolunteerApplication extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(KEFVolunteerApplication.class, args);
    }
}
