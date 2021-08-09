package com.mbtizip.repository.comment;

import com.mbtizip.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{

    private final EntityManager em;

    @Override
    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public Comment find(Long commentId) {
        return em.find(Comment.class, commentId);
    }
}
