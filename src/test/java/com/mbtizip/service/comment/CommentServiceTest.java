package com.mbtizip.service.comment;

import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.comment.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.mbtizip.common.util.TestEntityGenerator.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository mockCommentRepository;

    @BeforeEach
    public void setUp(){
        commentService = new CommentServiceImpl(mockCommentRepository);
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
                ()->commentService.addComment(person, comment));
    }

}
