package com.mbtizip.repository.candidate;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.QCandidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.category.QCategory;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.mbti.QMbti;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAQueryBase;
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
    public List<Candidate> findAll(String dType, Page page) {
        return null;
    }

    @Override
    public List<Candidate> findAll(String dType, Page page, OrderSpecifier sort) {
        return null;
    }

    @Override
    public List<Candidate> findAll(String dType, Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return null;
    }

    @Override
    public Long countAll(String dType, Page page) {
        return null;
    }

    @Override
    public Long countAll(String dType, Page page, OrderSpecifier sort) {
        return null;
    }

    @Override
    public Long countAll(String dType, Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return null;
    }

    @Override
    public void remove(Candidate candidate) {
        em.remove(candidate);
    }

    @Override
    public Long countByNameOrTitle(String dType, String title) {
        return null;
    }

    private JPAQueryBase getJoinQuery(){
        final QCategory category = QCategory.category;
        final QCandidate candidate = QCandidate.candidate;
        final QFile file = QFile.file;
        final QMbti mbti = QMbti.mbti;

        return queryFactory.selectFrom(candidate)
                .leftJoin(candidate.mbti, mbti)
                .fetchJoin()
                .fetchJoin()
                .leftJoin(candidate.file, file)
                .fetchJoin();
    }
}
