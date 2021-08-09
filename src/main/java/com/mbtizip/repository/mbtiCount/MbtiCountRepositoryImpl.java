package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.TooManyEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

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

    @Override
    public void modifyJobCount(Mbti mbti, Job job, boolean isIncrease) {
        List<MbtiCount> mbtiCounts = getMbtiCounts(mbti, job, job.getId());

        // 등록된 카운트가 없으면 persist 후 update
        if(mbtiCounts.size() == 0){
            MbtiCount mbtiCount = MbtiCount.builder()
                    .mbti(mbti)
                    .job(job)
                    .build();
            save(mbtiCount);
            mbtiCount.updateCount(isIncrease);
        } else {
            mbtiCounts.get(0).updateCount(isIncrease);
        }
    }


    @Override
    public void modifyPersonCount(Mbti mbti, Person person, boolean isIncrease) {



        List<MbtiCount> mbtiCounts = getMbtiCounts(mbti, person, person.getId());
        mbtiCounts.get(0).updateCount(isIncrease);

        if(mbtiCounts.size() == 0){
            MbtiCount mbtiCount = MbtiCount.builder()
                    .mbti(mbti)
                    .person(person)
                    .build();
            save(mbtiCount);
            mbtiCount.updateCount(isIncrease);
        } else {
            mbtiCounts.get(0).updateCount(isIncrease);
        }
    }

    // == private 메서드 == //
    private List<MbtiCount> getMbtiCounts(Mbti mbti, Object obj, Long id) {

        String str = "";

        if(obj instanceof Job){
            str = "job.";
        } else if (obj instanceof Person){
            str = "person.";
        }

        List<MbtiCount> mbtiCounts = em.createQuery("select c from MbtiCount c" +
                        " where c.mbti.id =: mbtiId" +
                        " and c." + str + "id =: id")
                .setParameter("mbtiId", mbti.getId())
                .setParameter("id", id)
                .getResultList();

        if(mbtiCounts.size() > 1){
            throw new TooManyEntityException("엔티티는 하나여야 합니다.");
        }

        return mbtiCounts;
    }


}
