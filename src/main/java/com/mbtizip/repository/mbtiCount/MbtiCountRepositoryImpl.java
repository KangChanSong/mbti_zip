package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.mbtiCount.QMbtiCount;
import com.mbtizip.exception.TooManyEntityException;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

@Repository
@RequiredArgsConstructor
public class MbtiCountRepositoryImpl implements MbtiCountRepository {

    private final EntityManager em;
    private final MbtiRepository mbtiRepository;

    @Override
    public List<Object[]> findAllByCandidate(Long candidateId) {
        return (List<Object[]>) em.createQuery("select mc, m from MbtiCount mc" +
                        " left join fetch Mbti m on mc.mbti.id = m.id" +
                        " where mc.candidate.id = :id")
                .setParameter("id", candidateId)
                .getResultList();
    }

    @Override
    public List<MbtiCount> findMaxByCandidate(Candidate candidate) {
        return em.createQuery(
                        "select c from MbtiCount c " +
                                " where c.candidate.id =: id and c.count in" +
                                "  (select max(c.count) from MbtiCount c" +
                                " join c.mbti" +
                                " where c.candidate.id =: id) ")
                .setParameter("id", candidate.getId())
                .getResultList();
    }

    @Override
    public void modifyCandidateCount(Mbti mbti, Candidate candidate, boolean isIncrease) {
        List<MbtiCount> mbtiCounts = findAllWithMbtiAndCandidate(mbti, candidate.getId());

        // mbtiCounts.size() == 0 일때만 builder 실행
        checkAndUpdate(mbtiCounts,
                MbtiCount.builder()
                        .mbti(mbti)
                        .candidate(candidate)
                        .build(), isIncrease);
    }

    @Override
    public void removeAllByCandidate(Candidate candidate) {
        em.createQuery("delete from MbtiCount  m" +
                        " where m.candidate.id =: id")
                .setParameter("id", candidate.getId())
                .executeUpdate();
    }

    @Override
    public void insertAllByCandidate(Candidate candidate) {

        List<Mbti> mbtis = mbtiRepository.findAll();
        mbtis.forEach(
                mbti ->  {
                    if(!mbti.getName().equals(MbtiEnum.NONE) && !mbti.getName().equals(MbtiEnum.DRAW)){
                        em.persist(MbtiCount.builder()
                                .mbti(mbti)
                                .candidate(candidate)
                                .build());
                    }
                });
    }

    @Override
    public Long sumAllOfCandidate(Long candidateId) {
        return (Long) em.createQuery("select sum(m.count) from MbtiCount m " +
                        " where m.candidate.id =: id" +
                        " group by m.candidate.id")
                .setParameter("id", candidateId)
                .getSingleResult();
    }


    // == private 메서드 == //

    private void checkAndUpdate(List<MbtiCount> mbtiCounts, MbtiCount mbtiCount, boolean isIncrease){
            if(mbtiCounts.size() == 0){
                save(mbtiCount);
                mbtiCount.updateCount(isIncrease);
            } else {
                mbtiCounts.get(0).updateCount(isIncrease);
            }
    }

    private List<MbtiCount> findAllWithMbtiAndCandidate(Mbti mbti, Long candidateId) {

        List<MbtiCount> mbtiCounts = em.createQuery("select c from MbtiCount c" +
                        " where c.mbti.id =: mbtiId" +
                        " and c.candidate.id =: id")
                .setParameter("mbtiId", mbti.getId())
                .setParameter("id", candidateId)
                .getResultList();

        if(mbtiCounts.size() > 1){
            throw new TooManyEntityException("엔티티는 하나여야 합니다.");
        }

        return mbtiCounts;
    }

    private void save(MbtiCount mbtiCount){
        em.persist(mbtiCount);
    }

}
