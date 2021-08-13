package com.mbtizip.service.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface CommentService {
    Long addComment(Person person, Comment comment);
    Long addComment(Job job, Comment comment);

    List<Comment> findAllByPerson(Person person, Page page, OrderSpecifier sort);
    List<Comment> findAllByJob(Job job, Page page, OrderSpecifier sort);

    void delete(Comment comment);
}
