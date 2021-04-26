package com.lzz.project.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lzz")
public class ProjectStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectStartApplication.class, args);
    }

}
