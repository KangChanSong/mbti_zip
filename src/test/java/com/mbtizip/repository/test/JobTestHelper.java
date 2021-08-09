package com.mbtizip.repository.test;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.repository.job.JobRepository;

public class JobTestHelper {

    public static final String JOB_TITLE = "개발자";

    JobRepository jobRepository;

    public JobTestHelper(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(){
        Job job = Job.builder().title(JOB_TITLE).build();
        jobRepository.save(job);
        return job;
    }


    public Job createJobWithMbti(Mbti mbti){
        Job job = Job.builder()
                .mbti(mbti)
                .title(JOB_TITLE).build();
        jobRepository.save(job);
        return job;
    }
}
