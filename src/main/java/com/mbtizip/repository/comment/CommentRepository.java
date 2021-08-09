package com.mbtizip.repository.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface CommentRepository {
    Long save(Comment comment);
    Comment find(Long commentId);

    List<Comment> findAllByJob(Job job);
    List<Comment> findAllByPerson(Person person);

    void delete(Comment comment);
}
