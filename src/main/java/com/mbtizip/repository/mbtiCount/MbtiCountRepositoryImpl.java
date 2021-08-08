package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.mbtiCount.MbtiCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MbtiCountRepositoryImpl implements MbtiCountRepository {

    private final EntityManager em;

    @Override
    public Long save(MbtiCount mbtiCount){
        em.persist(mbtiCount);
        return mbtiCount.getId();
    }
    @Override
    public MbtiCount find(Long id){
        return em.find(MbtiCount.class, id);
    }

    @Override
    public List<MbtiCount> findAllByJob(Long jobId) {
        return em.createQuery("select c from MbtiCount c " +
                "where c.job.id =: jobId")
                .setParameter("jobId", jobId)
                .getResultList();
    }

    @Override
    public List<MbtiCount> findAllByPerson(Long personId) {
        return em.createQuery("select c from MbtiCount c " +
                        "where c.person.id =: personId")
                .setParameter("personId", personId)
                .getResultList();
    }

    @Override
    public MbtiCount findMaxByJob(Long jobId) {
        return (MbtiCount) em.createQuery("select c from MbtiCount c" +
                " where c.job.id =: jobId order by c.count desc")
                .setParameter("jobId", jobId)
                .getResultList().get(0);
    }

    @Override
    public MbtiCount findMaxByPerson(Long personId) {
        return (MbtiCount) em.createQuery("select c from MbtiCount c" +
                        " where c.person.id =: personId order by c.count desc")
                .setParameter("personId", personId)
                .getResultList().get(0);
    }

}
