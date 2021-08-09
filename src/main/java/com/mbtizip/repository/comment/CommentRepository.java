package com.mbtizip.repository.comment;

import com.mbtizip.domain.comment.Comment;

public interface CommentRepository {
    Long save(Comment comment);
    Comment find(Long commentId);
}
