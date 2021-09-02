package com.mbtizip.repository.job;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.common.CommonRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepository{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

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
    public List<Job> findAll(Page page) {
        return joinQuery()
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public List<Job> findAll(Page page, OrderSpecifier sort) {
        return joinQuery()
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public List<Job> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return joinQuery()
                .where(keyword)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    private JPAQuery<Job> joinQuery(){
        QJob job = QJob.job;
        QMbti mbti = QMbti.mbti;
        return queryFactory.selectFrom(job)
                .leftJoin(job.mbti, mbti);
    }

    @Override
    public void modifyLikes(Job job, Boolean isIncrease) {
        CommonRepository.modifyLikes(em, Job.class, job.getId(), isIncrease );
    }

    @Override
    public void changeMbti(Job job, Mbti mbti) {
        CommonRepository.changeMbti(em, Job.class, job.getId(), mbti);
    }

    @Override
    public void remove(Job job) {
        em.remove(job);
    }

    @Override
    public Long countAll() {
    return (Long) em.createQuery("select count(j) from Job j")
            .getSingleResult();
    }

}
