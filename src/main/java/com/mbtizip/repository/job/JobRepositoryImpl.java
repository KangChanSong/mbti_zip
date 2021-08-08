package com.mbtizip.repository.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.exception.NoEntityFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepository{

    private final EntityManager em;

    @Override
    public Long save(Job job) {
        em.persist(job);
        return job.getId();
    }

    @Override
    public Job find(Long id){
        Job job = em.find(Job.class, id);
        if(job == null) throw new NoEntityFoundException("Job 을 찾을 수 없습니다. id = " +  id);
        return job;
    }
}
