package com.mbtizip.repository.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.common.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    @Override
    public List<Job> findAllWithMbti() {
        return em.createQuery("select j from Job j " +
                "join fetch j.mbti")
                .getResultList();
    }

    @Override
    public List<Job> findAllByMbti(Mbti mbti) {
        return em.createQuery("select j from Job j" +
                        " join fetch j.mbti" +
                        " where j.mbti.id =: mbtiId")
                .setParameter("mbtiId", mbti.getId())
                .getResultList();
    }

    @Override
    public void modifyLikes(Job job, Boolean isIncrease) {
        CommonRepository.modifyLikes(em, Job.class, job.getId(), isIncrease );
    }

    @Override
    public void changeMbti(Job job, Mbti mbti) {
        CommonRepository.changeMbti(em, Job.class, job.getId(), mbti);
    }


}
