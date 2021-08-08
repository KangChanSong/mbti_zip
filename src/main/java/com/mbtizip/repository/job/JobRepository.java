package com.mbtizip.repository.job;

import com.mbtizip.domain.job.Job;

public interface JobRepository {

    Long save(Job job);
    Job find(Long id);
}
