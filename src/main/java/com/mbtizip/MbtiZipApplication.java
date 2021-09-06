package com.mbtizip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MbtiZipApplication {
    public static void main(String[] args) {
        SpringApplication.run(MbtiZipApplication.class, args);
    }

}
