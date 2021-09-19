package com.mbtizip.repository.candidate;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.QCandidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.job.QJob;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.candidate.person.QPerson;
import com.mbtizip.domain.category.QCategory;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.mbti.QMbti;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CandidateRepositoryImpl implements CandidateRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    @Override
    public Long save(Candidate candidate) {
        em.persist(candidate);
        return candidate.getId();
    }

    @Override
    public Candidate find(Long id) {
        return em.find(Candidate.class, id);
    }

    @Override
    public <T extends Candidate> List<T> findAll(Class<T> cls, Page page) {
        return getJoinQuery(cls)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public <T extends Candidate> List<T> findAll(Class<T> cls, Page page, OrderSpecifier sort) {
        return getJoinQuery(cls)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public <T extends Candidate> List<T> findAll(Class<T> cls, Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return getJoinQuery(cls)
                .where(keyword)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public <T extends Candidate> Long countAll(Class<T> cls) {
        return getJoinQuery(cls).fetchCount();
    }

    @Override
    public <T extends Candidate> Long countAll(Class<T> cls, BooleanExpression keyword) {
        return getJoinQuery(cls)
                .where(keyword)
                .fetchCount();
    }

    @Override
    public void remove(Candidate candidate) {
        em.remove(candidate);
    }

    //== private 메서드 ==//

    private <T extends Candidate> JPAQuery<T> getJoinQuery(Class<T> cls) {
        return checkAndGetQuery(cls,
                getPersonJoinQuery(),
                getJobJoinQuery());
    }

    private <T extends Candidate> JPAQuery<T> checkAndGetQuery(Class<T> cls, JPAQuery<T> personQuery, JPAQuery<T> jobQuery){
        if (!cls.equals(Person.class) && !cls.equals(Job.class))
            throw new IllegalArgumentException("Class 는 Job 이나 Person 이어야 합니다.");
        if (cls.equals(Person.class)) return personQuery;
        if (cls.equals(Job.class)) return jobQuery;

        return null;
    }

    private JPAQuery getPersonJoinQuery(){
        final QPerson person = QPerson.person;
        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti, QMbti.mbti)
                .fetchJoin()
                .leftJoin(person.file, QFile.file)
                .fetchJoin()
                .innerJoin(person.category, QCategory.category)
                .fetchJoin();
    }

    private JPAQuery getJobJoinQuery(){
        final QJob job = QJob.job;
        return queryFactory.selectFrom(job)
                .leftJoin(job.mbti, QMbti.mbti)
                .fetchJoin()
                .leftJoin(job.file, QFile.file)
                .fetchJoin();
    }
}
