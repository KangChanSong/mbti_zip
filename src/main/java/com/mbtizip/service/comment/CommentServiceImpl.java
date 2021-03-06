package com.mbtizip.service.comment;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.comment.CommentRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
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
        if (isMatch(password, comment.getPassword())) {
            commentRepository.remove(comment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long getTotalCount(Long candidateId) {
        return commentRepository.countAll(candidateId);
    }

    //== private ????????? ==//
    private Comment checkIfNullAndReturn(Long commentId){
        Comment comment = commentRepository.find(commentId);
        if(comment == null) throw new IllegalArgumentException("????????? ?????? ??? ????????????. id : " + commentId);
        return comment;
    }

}
