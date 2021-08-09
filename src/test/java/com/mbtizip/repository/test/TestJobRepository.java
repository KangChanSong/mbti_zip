package com.mbtizip.repository.test;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.repository.job.JobRepository;

import javax.persistence.EntityManager;

public class TestJobRepository {

    public static final String JOB_TITLE = "개발자";

    EntityManager em;

    public TestJobRepository(EntityManager em) {
        this.em = em;
    }

    public Job createJob(){
        Job job = Job.builder().title(JOB_TITLE).build();
        em.persist(job);
        return job;
    }


    public Job createJobWithMbti(Mbti mbti){
        Job job = Job.builder()
                .mbti(mbti)
                .title(JOB_TITLE).build();
        em.persist(job);
        return job;
    }
}
