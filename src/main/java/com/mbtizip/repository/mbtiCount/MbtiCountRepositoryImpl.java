package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.TooManyEntityException;
import com.mbtizip.repository.common.CommonRepository;
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
    public List<MbtiCount> findAllByJob(Job job) {
        return (List<MbtiCount>) CommonRepository.findAllByObject(em, MbtiCount.class, Job.class, job.getId());
    }

    @Override
    public List<MbtiCount> findAllByPerson(Person person) {
        return (List<MbtiCount>) CommonRepository.findAllByObject(em, MbtiCount.class, Person.class,  person.getId());
    }

    @Override
    public List<MbtiCount> findMaxByJob(Job job) {
        return findMaxByObject(job, job.getId());
    }

    @Override
    public List<MbtiCount> findMaxByPerson(Person person) {
        return findMaxByObject(person, person.getId());
    }

    @Override
    public void modifyJobCount(Mbti mbti, Job job, boolean isIncrease) {
        List<MbtiCount> mbtiCounts = findAllWithMbtiAndOjb(mbti, job, job.getId());

        // mbtiCounts.size() 가 0 일때만 builder 실행
        checkAndUpdate(mbtiCounts,
                () -> MbtiCount.builder()
                        .mbti(mbti)
                        .job(job)
                        .build(), isIncrease);
    }


    @Override
    public void modifyPersonCount(Mbti mbti, Person person, boolean isIncrease) {

        List<MbtiCount> mbtiCounts = findAllWithMbtiAndOjb(mbti, person, person.getId());

        checkAndUpdate(mbtiCounts,
                () -> MbtiCount.builder()
                    .mbti(mbti)
                    .person(person)
                    .build(), isIncrease);

    }

    @Override
    public void removeAllByPerson(Person person) {
        removeAllByObject(person, person.getId());
    }

    @Override
    public void removeAllByJob(Job job) {
        removeAllByObject(job, job.getId());
    }

    private void removeAllByObject(Object obj, Long id){
        checkType(obj);
        String str = obj.getClass().getSimpleName().toLowerCase(Locale.ROOT) + ".";
        em.createQuery("delete from MbtiCount  m" +
                " where m. "+ str + "id =: id")
                .setParameter("id", id)
                .executeUpdate();
    }

    // == private 메서드 == //

    private List<MbtiCount> findMaxByObject(Object obj , Long id){
        checkType(obj);
        String str = obj.getClass().getSimpleName().toLowerCase(Locale.ROOT) + ".";
        return em.createQuery(
                            "select c from MbtiCount c " +
                                    " where c." + str + "id =: id and c.count in" +
                "  (select max(c.count) from MbtiCount c" +
                        " join c.mbti" +
                        " where c." + str + "id =: id) ")
                .setParameter("id", id)
                .getResultList();
    }

    private void checkAndUpdate(List<MbtiCount> mbtiCounts, CountGeneratorFunc func , boolean isIncrease){
            if(mbtiCounts.size() == 0){
                MbtiCount mbtiCount = func.generate();
                save(mbtiCount);
                mbtiCount.updateCount(isIncrease);
            } else {
                mbtiCounts.get(0).updateCount(isIncrease);
            }
    }

    private List<MbtiCount> findAllWithMbtiAndOjb(Mbti mbti, Object obj, Long id) {

        String str = obj.getClass().getSimpleName().toLowerCase(Locale.ROOT) + ".";

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

    private void save(MbtiCount mbtiCount){
        em.persist(mbtiCount);
    }

    private void checkType(Object obj){
        Class cls = obj.getClass();
        if(cls != Person.class && cls != Job.class){
            throw new IllegalArgumentException("인스턴스 타입이 맞지 않습니다,");
        }
    }

    //== static 클래스 ==//
    @FunctionalInterface
    interface CountGeneratorFunc{
        MbtiCount generate();
    }

}
