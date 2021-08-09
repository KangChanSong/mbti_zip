package com.mbtizip.repository.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.common.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

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

    @Override
    public List<Comment> findAllByJob(Job job) {
        return (List<Comment>) CommonRepository.findAllByObject(em, Comment.class, Job.class, job.getId());
    }

    @Override
    public List<Comment> findAllByPerson(Person person) {
        return (List<Comment>) CommonRepository.findAllByObject(em, Comment.class, Person.class, person.getId());
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }

}
