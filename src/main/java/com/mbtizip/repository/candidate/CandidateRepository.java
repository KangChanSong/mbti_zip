package com.mbtizip.repository.candidate;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface CandidateRepository {

    Long save(Candidate candidate);
    Candidate find(Long id);

    List<Candidate> findAll(String dType, Page page);
    List<Candidate> findAll(String dType, Page page , OrderSpecifier sort);
    List<Candidate> findAll(String dType, Page page, OrderSpecifier sort, BooleanExpression keyword);

    Long countAll(String dType, Page page);
    Long countAll(String dType, Page page , OrderSpecifier sort);
    Long countAll(String dType, Page page, OrderSpecifier sort, BooleanExpression keyword);

    void remove(Candidate candidate);

    Long countByNameOrTitle(String dType, String title);
}
