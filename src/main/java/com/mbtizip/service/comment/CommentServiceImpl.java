package com.mbtizip.service.comment;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.comment.CommentRepository;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.util.ErrorMessageProvider;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

import static com.mbtizip.util.EncryptHelper.encrypt;
import static com.mbtizip.util.EncryptHelper.isMatch;
import static com.mbtizip.util.ErrorMessageProvider.NO_ENTITY_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public Boolean addComment(Long candidateId, Comment comment) {
        Candidate candidate = candidateRepository.find(candidateId);
        if(candidate == null) throw new IllegalArgumentException(NO_ENTITY_FOUND + " id = " + candidateId);

        comment.setPassword(encrypt(comment.getPassword()));
        commentRepository.save(comment);
        comment.setCandidate(candidate);

        return comment.getId() == null ? false : true;
    }

    @Override
    public Comment find(Long commentId) {
        return commentRepository.find(commentId);
    }

    @Override
    public List<Comment> findAll(Long candidateId, Page page, OrderSpecifier sort) {
        if(page == null) page = Page.builder().build();
        if(sort == null) sort = QComment.comment.createDate.desc();

        BooleanExpression keyword = QComment.comment.candidate.id.eq(candidateId);

        return commentRepository.findAll(page, sort , keyword);
    }

    @Transactional
    @Override
    public Boolean update(Long commentId, Comment comment) {
        Comment findComment = checkIfNullAndReturn(commentId);
        findComment.update(comment);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Long commentId, String password) {
        Comment comment = checkIfNullAndReturn(commentId);
        if(isMatch(password, comment.getPassword())){
            commentRepository.remove(comment);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean like(Long commentId) {
        Comment comment = checkIfNullAndReturn(commentId);
        comment.modifyLikes(true);
        return true;
    }

    @Transactional
    @Override
    public Boolean cancelLike(Long commentId) {
        Comment comment = checkIfNullAndReturn(commentId);
        comment.modifyLikes(false);
        return true;
    }

    @Override
    public Long getTotalCount(Long candidateId) {
        return commentRepository.countAll(candidateId);
    }

    //== private 메서드 ==//
    private Comment checkIfNullAndReturn(Long commentId){
        Comment comment = commentRepository.find(commentId);
        if(comment == null) throw new IllegalArgumentException("댓글을 찾을 수 없습니다. id : " + commentId);
        return comment;
    }

}
