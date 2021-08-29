package com.mbtizip.dummies;

import com.mbtizip.domain.job.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class JobDummyRepository {

    private final EntityManager em;

    @Transactional
    public void save(Job job){
        em.persist(job);
    }
    @Transactional
    public void insertJobs(){
        IntStream.range(0, 50).forEach( i-> {
            save(Job.builder()
                    .title("title" + i)
                    .writer("writer" + i)
                    .password("password" + i)
                    .build());
        });
    }
}
