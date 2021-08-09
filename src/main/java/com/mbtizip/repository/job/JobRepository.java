package com.mbtizip.repository.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;

import java.util.List;

public interface JobRepository {

    Long save(Job job);
    Job find(Long id);
    List<Job> findAllWithMbti();
    List<Job> findAllByMbti(Mbti mbti);
    void modifyLikes(Job job, Boolean isIncrease);
    void changeMbti(Job job, Mbti mbti);
}
