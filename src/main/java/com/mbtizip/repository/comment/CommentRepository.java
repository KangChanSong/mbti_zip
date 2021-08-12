package com.mbtizip.repository.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface CommentRepository {
    Long save(Comment comment);
    Comment find(Long commentId);
    List<Comment> findAll(Page page, OrderSpecifier sort);
    List<Comment> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);

    void remove(Comment comment);
    void modifyLikes(Comment comment, Boolean isIncrease);
}
