package com.mbtizip.repository.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.repository.common.CommonRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.TypeCache;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

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
                .leftJoin(qComment.person, QPerson.person)
                .leftJoin(qComment.job, QJob.job);
    }


    @Override
    public void remove(Comment comment) {
        em.remove(comment);
    }

    @Override
    public void modifyLikes(Comment comment, Boolean isIncrease) {
        CommonRepository.modifyLikes(em, Comment.class, comment.getId(), isIncrease);
    }

}
