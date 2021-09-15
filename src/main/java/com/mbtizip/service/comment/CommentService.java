package com.mbtizip.service.comment;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface CommentService {
    Boolean addComment(Long candidateId , Comment comment);

    Comment find(Long commentId);

    List<Comment> findAll(Long candidateId, Page page, OrderSpecifier sort);
    Boolean update(Long commentId, Comment comment);
    Boolean delete(Long commentId, String password);

    Boolean like(Long commentId);
    Boolean cancelLike(Long commentId);

    Long getTotalCount(Long candidateId);
}
