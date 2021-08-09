package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.repository.comment.CommentRepository;
import com.mbtizip.repository.test.TestJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    private static final String COMMENT_CONTENT = "댓글내용";

    TestJobRepository testJobRepository;

    @BeforeEach
    public void setUp(){
        testJobRepository = new TestJobRepository(em);
    }

    @Test
    public void 직업_댓글_등록과_조회(){
        
        //given
        Comment comment = createCommentWithJob();

        //when
        Long saveId = commentRepository.save(comment);
        Comment findComment = commentRepository.find(saveId);
        //then

        assertTrue(saveId > 0);
        assertEquals(findComment.getContent(), COMMENT_CONTENT);
    }

    @Test
    public void 직업_댓글_목록_조회(){

        //given
        Comment comment1 = createCommentWithJob();
        Comment comment2 = createCommentWithJob();
        Comment comment3 = createCommentWithJob();

        //when
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        //then

    }

    @Test
    public void 직업_댓글_수정(){

    }

    @Test
    public void 직업_댓글_삭제(){

    }

    private Comment createCommentWithJob(){
        Job job = testJobRepository.createJob();

        Comment comment = Comment.builder()
                .content(COMMENT_CONTENT)
                .job(job)
                .build();

        return comment;
    }
}
