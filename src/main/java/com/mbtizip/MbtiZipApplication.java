package com.mbtizip;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MbtiZipApplication {
    public static void main(String[] args) {
        SpringApplication.run(MbtiZipApplication.class, args);
    }
}
