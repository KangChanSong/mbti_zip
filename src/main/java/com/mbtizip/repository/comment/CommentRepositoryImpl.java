package com.mbtizip.repository.comment;

import com.mbtizip.domain.candidate.QCandidate;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.mbti.QMbti;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    @Override
    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public Comment find(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public List<Comment> findAll(Page page, OrderSpecifier sort) {
        return joinQuery()
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public List<Comment> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return joinQuery()
                .where(keyword)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    private JPAQuery<Comment> joinQuery(){
        QComment qComment = QComment.comment;
        return queryFactory.selectFrom(qComment)
                .leftJoin(qComment.mbti, QMbti.mbti)
                .leftJoin(qComment.candidate, QCandidate.candidate);
    }
    @Override
    public void remove(Comment comment) {
        em.remove(comment);
    }

    @Override
    public Long countAll(Long candidateId) {

        return (Long) em.createQuery("select count(c) from Comment c " +
                "where c.candidate.id = :id" )
                .setParameter("id",candidateId )
                .getSingleResult();
    }

}
