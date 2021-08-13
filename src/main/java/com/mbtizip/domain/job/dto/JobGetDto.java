package com.mbtizip.domain.job.dto;

import com.mbtizip.domain.job.Job;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class JobGetDto {

    private Long id;
    private String title;
    private String writer;
    private Integer likes;
    private String mbti;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static JobGetDto toDto(Job job){
        return JobGetDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .writer(job.getWriter())
                .likes(job.getLikes())
                .mbti(vaildateAndReturnMbti(job))
                .createDate(job.getCreateDate())
                .updateDate(job.getUpdateDate())
                .build();
    }

    private static String vaildateAndReturnMbti(Job job) {

        if(job.getMbti() == null){
            return null;
        } else {
            return job.getMbti().getName().getText();
        }
    }


}