package com.mbtizip.service.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;

import java.util.List;

public interface JobService {
    Long register(Job job);
    Job get(Long id);
    void increaseMbtiCount(Mbti mbti, Job job);
    void decreaseMbtiCount(Mbti mbti, Job job);
    List<Job> findAllWithMbti();
}
