package com.mbtizip.service.job;

import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface JobService {
    Long register(Job job);
    Job get(Long id);
    List<Job> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);
    List<Job> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId);
    void delete(Job job);
}
