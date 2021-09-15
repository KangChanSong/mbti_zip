package com.mbtizip.domain.candidate.job.dto;

import com.mbtizip.domain.candidate.job.Job;
import lombok.Data;

@Data
public class JobRegisterDto {

    private String title;
    private String writer;
    private String password;
    private String filename;

    public Job toEntity(){
        return Job.builder()
                .title(this.title)
                .writer(this.writer)
                .password(this.password)
                .build();
    }
}
