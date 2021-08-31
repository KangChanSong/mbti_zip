package com.mbtizip.service.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface CommentService {
    Boolean addPersonComment(Long personId, Comment comment);
    Boolean addJobComment(Long jobId, Comment comment);

    Comment find(Long commentId);

    List<Comment> findAllByPerson(Long personId, Page page, OrderSpecifier sort);
    List<Comment> findAllByJob(Long jobId, Page page, OrderSpecifier sort);

    Boolean update(Long commentId, Comment comment);
    Boolean delete(Long commentId, String password);

    Boolean like(Long commentId);
    Boolean cancelLike(Long commentId);

    Long getTotalCount(String target, Long targetId);
}
