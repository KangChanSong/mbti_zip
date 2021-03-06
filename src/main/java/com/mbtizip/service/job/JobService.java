package com.mbtizip.service.job;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.candidate.job.Job;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface JobService {
    Boolean register(Job job);
    Job get(Long id);

    List<Job> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);
    List<Job> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId);

    Long countAll(BooleanExpression keyword);

    Boolean delete(Long jobId, String password);

    Boolean vote(Long mbtiId, Long jobId);
    Boolean cancelVote(Long mbtiId, Long jobId);

    Boolean like(Long jobId);
    Boolean cancelLike(Long jobId);

    void increaseView(Long jobId);
    Boolean checkIfExists(String title);
}
