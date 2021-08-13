package com.mbtizip.domain.job.dto;

import com.mbtizip.domain.job.Job;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JobRegisterDto {

    private String title;
    private String writer;

    public Job toEntity(){
        return Job.builder()
                .title(this.title)
                .writer(this.writer)
                .build();
    }
}
