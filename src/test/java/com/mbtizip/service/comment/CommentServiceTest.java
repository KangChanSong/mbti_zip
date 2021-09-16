package com.mbtizip.service.comment;

import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.comment.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mbtizip.common.util.TestEntityGenerator.createComment;
import static com.mbtizip.common.util.TestEntityGenerator.createPerson;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository mockCommentRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void setUp(){
        commentService = new CommentServiceImpl(mockCommentRepository, candidateRepository);
    }


    @DisplayName("Person, Job 객체가 영속상태가 아닐 경우에 예외 발생")
    @Test
    public void 댓글_등록(){

        //given
        Person person = createPerson();
        Comment comment = createComment();
        //when

        //then
        assertThrows(IllegalArgumentException.class,
                ()->commentService.addComment(person.getId(), comment));
    }

}
