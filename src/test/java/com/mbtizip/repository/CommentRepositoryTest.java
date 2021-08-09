package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.comment.CommentRepository;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    private static final String COMMENT_CONTENT = "댓글내용";

    TestJobRepository testJobRepository;
    TestPersonRepository testPersonRepository;
    TestMbtiRepository testMbtiRepository;

    @BeforeEach
    public void setUp(){

        testPersonRepository = new TestPersonRepository(em);
        testJobRepository = new TestJobRepository(em);
        testMbtiRepository = new TestMbtiRepository(em);
    }

    @Test
    public void 직업_댓글_등록과_조회(){
        
        //given
        Job job = testJobRepository.createJob();
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Comment comment = createCommentWithJobAndMbti(mbti, job);

        //when
        Long saveId = commentRepository.save(comment);
        Comment findComment = commentRepository.find(saveId);
        //then

        assertTrue(saveId > 0);
        assertEquals(findComment.getContent(), COMMENT_CONTENT);
        assertEquals(findComment.getJob(), job);
        assertEquals(findComment.getMbti(), mbti);
    }

    @Test
    public void 직업_댓글_목록_조회(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJob();
        int count = 10;

        //when
        for(int i = 0; i < count ; i++){
            createCommentWithJobAndMbti(mbti, job);
        }
        //then
        List<Comment> comments = commentRepository.findAllByJob(job);

        assertEquals(comments.size(), count);
        comments.forEach( i -> assertSame(i.getJob(), job));
        comments.forEach( i -> assertSame(i.getMbti(), mbti));
    }

    @Test
    public void 인물_댓글_목록_조회(){

        //given
        Person person = testPersonRepository.createPerson();
        Mbti mbti = testMbtiRepository.findAll().get(0);
        int count = 10;
        //when
        for(int i = 0 ; i < count ; i++){
            createCommentWithPersonAndMbti(mbti, person);
        }
        //then
        List<Comment> comments = commentRepository.findAllByPerson(person);

        assertEquals(comments.size(), count);
        comments.forEach( i -> assertSame(i.getPerson(), person));
        comments.forEach( i -> assertSame(i.getMbti(), mbti));
    }

    @Test
    public void 직업_댓글_수정(){

        //given
        Job job = testJobRepository.createJob();
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Comment comment = createCommentWithJobAndMbti(mbti, job);

        String modifiedContent = "수정된내용";

        Comment modify = Comment.builder()
                .id(comment.getId())
                .content(modifiedContent)
                .build();

        //when
        comment.update(modify);
        Comment findComment = commentRepository.find(comment.getId());

        //then
        assertEquals(findComment.getContent(), modifiedContent);
        assertEquals(findComment.getContent(), comment.getContent());
        assertSame(findComment.getMbti(), mbti);
        assertSame(findComment.getJob(), job);
        assertSame(findComment, comment);

    }

    @Test
    public void 직업_댓글_삭제(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJob();
        //when
        Comment comment = createCommentWithJobAndMbti(mbti, job);
        commentRepository.delete(comment);

        //then
        List<Comment> findComments = commentRepository.findAllByJob(job);
        assertEquals(findComments.size(), 0);
        assertThrows(IndexOutOfBoundsException.class, ()->{
           findComments.get(0);
        });
    }

    @Test
    public void 좋아요_증감(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJob();
        //when
        Comment comment = createCommentWithJobAndMbti(mbti, job);
        commentRepository.modifyLikes(comment, true);
        commentRepository.modifyLikes(comment, false);

        //then
        Comment findComment = commentRepository.find(comment.getId());
        assertEquals(findComment.getLikes(), 0);

    }

    private Comment createCommentWithJobAndMbti(Mbti mbti, Job job){
        Comment comment = Comment.builder()
                .content(COMMENT_CONTENT)
                .mbti(mbti)
                .job(job)
                .build();
        commentRepository.save(comment);

        return comment;
    }

    private Comment createCommentWithPersonAndMbti(Mbti mbti, Person person){

        Comment comment = Comment.builder()
                .content(COMMENT_CONTENT)
                .mbti(mbti)
                .person(person)
                .build();
        commentRepository.save(comment);

        return comment;
    }
}
