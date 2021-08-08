package com.mbtizip.repository.job;

import com.mbtizip.domain.job.Job;

import java.util.List;

public interface JobRepository {

    Long save(Job job);
    Job find(Long id);
    List<Job> findAllWithMbti();
}
