package com.mbtizip.repository.candidate;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface CandidateRepository {

    Long save(Candidate candidate);
    Candidate find(Long id);

    <T extends Candidate> List<T> findAll(Class<T> cls, Page page);
    <T extends Candidate> List<T> findAll(Class<T> cls, Page page , OrderSpecifier sort);
    <T extends Candidate> List<T> findAll(Class<T> cls, Page page, OrderSpecifier sort, BooleanExpression keyword);

    <T extends Candidate> Long countAll(Class<T> cls);
    <T extends Candidate> Long countAll(Class<T> cls,  BooleanExpression keyword);

    void remove(Candidate candidate);

}
