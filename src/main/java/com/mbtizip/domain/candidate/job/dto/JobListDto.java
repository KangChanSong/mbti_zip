package com.mbtizip.domain.candidate.job.dto;

import com.mbtizip.domain.candidate.job.Job;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class JobListDto {

    private List<JobGetDto> jobGetDtos;

    public static JobListDto toDto(List<Job> jobs){
        return JobListDto.builder()
                .jobGetDtos(toDtoList(jobs))
                .build();
    }

    private static List<JobGetDto> toDtoList(List<Job> jobs) {
        return jobs.stream().map(job -> JobGetDto.toDto(job))
                .collect(Collectors.toList());
    }

}
