package com.mbtizip.service.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.repository.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;


    @Override
    public Long register(Job job) {
        if(job == null) throw new IllegalArgumentException("Job 이 존재하지 않습니다.");
        return jobRepository.save(job);
    }

    @Override
    public Job get(Long id) {
        return jobRepository.find(id);
    }

    @Override
    public void increaseMbtiCount(Mbti mbti, Job job) {

        // mbtiCount 집계

        // 최댓값을 Job.mbti 에 할당



    }

    @Override
    public void decreaseMbtiCount(Mbti mbti, Job job) {

    }

    @Override
    public List<Job> findAllWithMbti() {
        return null;
    }
}
