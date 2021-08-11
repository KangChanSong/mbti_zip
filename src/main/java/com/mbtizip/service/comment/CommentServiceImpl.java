package com.mbtizip.service.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.comment.CommentRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Long addComment(Person person, Comment comment) {
        checkIfPersisted(person.getId());
        commentRepository.save(comment);
        comment.setPerson(person);
        return comment.getId();
    }

    @Override
    public Long addComment(Job job, Comment comment) {
        checkIfPersisted(job.getId());
        commentRepository.save(comment);
        comment.setJob(job);
        return comment.getId();
    }

    @Override
    public List<Comment> findAllByPerson(Person person, Page page, OrderSpecifier sort) {
        BooleanExpression keyword = QComment.comment.person.eq(person);
        return commentRepository.findAll(page, sort, keyword);
    }

    @Override
    public List<Comment> findAllByJob(Job job , Page page, OrderSpecifier sort) {
        BooleanExpression keyword = QComment.comment.job.eq(job);
        return commentRepository.findAll(page, sort , keyword);
    }

    private void checkIfPersisted(Long id){
        if(id == null) throw new IllegalArgumentException("객체가 영속상태여야 합니다. id : " + id);
    }
}
