package com.mbtizip.service.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.repository.comment.CommentRepository;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.person.PersonRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PersonRepository personRepository;
    private final JobRepository jobRepository;
    @Transactional
    @Override
    public Boolean addPersonComment(Long personId, Comment comment) {
        return addCommonComment(comment, () -> personRepository.find(personId));
    }
    @Transactional
    @Override
    public Boolean addJobComment(Long jobId, Comment comment) {
        return addCommonComment(comment, () -> jobRepository.find(jobId));
    }

    @Override
    public Comment find(Long commentId) {
        return commentRepository.find(commentId);
    }

    @Override
    public List<Comment> findAllByPerson(Long personId, Page page, OrderSpecifier sort) {
        Person person = personRepository.find(personId);
        if(person == null) throw new IllegalArgumentException("Person 을 찾을 수 없습니다. id : " + personId);

        return findAllByObject(person, page , sort);
    }

    @Override
    public List<Comment> findAllByJob(Long jobId , Page page, OrderSpecifier sort) {
        Job job = jobRepository.find(jobId);
        if(job == null) throw new IllegalArgumentException("Job 을 찾을 수 없습니다. id : " + jobId);

        return findAllByObject(job, page, sort);
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
    public Long getTotalCount(String target, Long targetId) {
        return commentRepository.countAll(target, targetId);
    }

    //== private 메서드 ==//
    private Comment checkIfNullAndReturn(Long commentId){
        Comment comment = commentRepository.find(commentId);
        if(comment == null) throw new IllegalArgumentException("댓글을 찾을 수 없습니다. id : " + commentId);
        return comment;
    }

    private Boolean addCommonComment(Comment comment, Supplier findQuery){
        Object obj = findQuery.get();
        if(obj == null) throw new IllegalArgumentException();

        comment.setPassword(encrypt(comment.getPassword()));
        commentRepository.save(comment);

        if(obj instanceof Job) comment.setJob((Job) obj);
        if (obj instanceof Person) comment.setPerson((Person) obj);

        return comment.getId() == null ? false : true;
    }

    private <T> List<Comment> findAllByObject(T obj, Page page ,OrderSpecifier sort){

        if(page == null) page = Page.builder().build();
        if(sort == null) sort = QComment.comment.createDate.desc();

        BooleanExpression keyword;

        if(obj instanceof Job) keyword = QComment.comment.job.eq((Job)obj);
        else if(obj instanceof Person) keyword = QComment.comment.person.eq((Person) obj);
        else throw new IllegalArgumentException("Person 이나 Job 의 인스턴스여야 합니다. 클래스명 : " + obj.getClass().getName());

        return commentRepository.findAll(page, sort , keyword);
    }
}
