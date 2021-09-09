package com.mbtizip.repository;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.comment.CommentRepository;
import com.mbtizip.repository.test.TestRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mbtizip.common.enums.TestCommentEnum.COMMENT_CONTENT;
import static com.mbtizip.common.util.TestEntityGenerator.createComment;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    private TestRepository testRepository;

    @BeforeEach
    public void setUp(){
        testRepository = new TestRepository(em);
    }

    @Test
    public void 직업_댓글_등록과_조회(){
        
        //given
        Job job = testRepository.getJobRepository().createJob();
        Mbti mbti = testRepository.getMbtiRepository().findAll().get(0);
        Comment comment = createComment();

        //when
        Long saveId = commentRepository.save(comment);
        comment.setJob(job);
        comment.setMbti(mbti);

        Comment findComment = commentRepository.find(saveId);
        //then

        assertTrue(saveId > 0);
        assertEquals(findComment.getContent(), COMMENT_CONTENT.getText());
        assertEquals(findComment.getJob(), job);
        assertEquals(findComment.getMbti(), mbti);
    }

    @Test
    public void 인물_댓글_목록_조회(){

        //given
        Person person = testRepository.getPersonRepository().createPerson();
        Mbti mbti = testRepository.getMbtiRepository().findAll().get(0);
        int count = 10;
        //when
        for(int i = 0 ; i < count ; i++){
            Comment comment = createComment();
            comment.setPerson(person);
            comment.setMbti(mbti);
        }

        Page page = Page.builder().pageNum(1).amount(10).build();
        OrderSpecifier sort = QComment.comment.createDate.desc();
        BooleanExpression keyword = QComment.comment.person.eq(person);

        //then
        List<Comment> comments = commentRepository.findAll(page, sort, keyword);

        assertEquals(comments.size(), count);
        comments.forEach( i -> assertSame(i.getPerson(), person));
        comments.forEach( i -> assertSame(i.getMbti(), mbti));
    }

    @Test
    public void 직업_댓글_수정(){

        //given
        Job job = testRepository.getJobRepository().createJob();
        Mbti mbti = testRepository.getMbtiRepository().findAll().get(0);
        Comment comment = createComment();
        commentRepository.save(comment);
        comment.setJob(job);
        comment.setMbti(mbti);

        String modifiedContent = "수정된내용";

        Comment modify = Comment.builder()
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
        Comment comment = createComment();
        commentRepository.save(comment);
        //when
        commentRepository.remove(comment);

        //then
        List<Comment> findComments = commentRepository.findAll(Page.builder().build(), QComment.comment.id.desc());
        assertEquals(findComments.size(), 0);
        assertThrows(IndexOutOfBoundsException.class, ()->{
           findComments.get(0);
        });
    }

    @Test
    public void 좋아요_증감(){

        //when
        Comment comment = createComment();
        commentRepository.save(comment);
        commentRepository.modifyLikes(comment, true);
        commentRepository.modifyLikes(comment, false);

        //then
        Comment findComment = commentRepository.find(comment.getId());
        assertEquals(findComment.getLikes(), 0);

    }

}
