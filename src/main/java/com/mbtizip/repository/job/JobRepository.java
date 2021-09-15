package com.mbtizip.repository.job;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.candidate.job.Job;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface JobRepository {

    Long save(Job job);
    Job find(Long id);

    List<Job> findAll(Page page);
    List<Job> findAll(Page page , OrderSpecifier sort);
    List<Job> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);

    void remove(Job job);

    Long countAll();
    Long countByTitle(String title);
}
