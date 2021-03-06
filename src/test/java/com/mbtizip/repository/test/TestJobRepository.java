package com.mbtizip.repository.test;

import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;

import javax.persistence.EntityManager;

public class TestJobRepository {

    public static final String JOB_TITLE = "개발자";
    public static final String JOB_WRITER ="송강찬";

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
                .title(JOB_TITLE).build();
        job.changeMbti(mbti);
        em.persist(job);
        return job;
    }

    public Job createJobWithMbti(String title, Mbti mbti){
        Job job = Job.builder()
                .title(title).build();
        job.changeMbti(mbti);
        em.persist(job);
        return job;
    }

}
