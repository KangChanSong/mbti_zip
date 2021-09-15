package com.mbtizip.repository.job;

import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.job.QJob;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.mbti.QMbti;
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

    private final QJob job = QJob.job;

    @Override
    public Long save(Job job) {
        em.persist(job);
        return job.getId();
    }

    @Override
    public Job find(Long id){
        return joinQuery().where(job.id.eq(id)).fetchOne();
    }

    @Override
    public List<Job> findAll(Page page) {
        return joinQuery()
                .where()
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
        QMbti mbti = QMbti.mbti;
        return queryFactory.selectFrom(job)
                .leftJoin(job.mbti, mbti)
                .fetchJoin()
                .leftJoin(job.file, QFile.file)
                .fetchJoin();
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

    @Override
    public Long countByTitle(String title) {
        return queryFactory.selectFrom(job)
                .where(job.title.eq(title))
                .fetchCount();
    }

}
