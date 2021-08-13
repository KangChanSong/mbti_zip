package com.mbtizip.controller.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.dto.CommentGetDto;
import com.mbtizip.domain.comment.dto.CommentListDto;
import com.mbtizip.domain.comment.dto.CommentRegisterDto;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.repository.comment.CommentRepository;
import com.mbtizip.service.comment.CommentService;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Slf4j
public class CommentApiController {

    private static final String TARGET_PERSON = "person";
    private static final String TARGET_JOB = "job";

    private final CommentService commentService;

    /**
     * @param target : 댓글이 달릴 대상 person or job
     * @param targetId : 댓글이 달릴 대상의 id personId or jobId
     */
    //댓글 등록
    @PostMapping("/api/v1/{target}/{targetId}/register")
    public BooleanResponseDto register(@PathVariable("target") String target,
                                       @PathVariable("targetId") Long targetId,
                                       @RequestBody CommentRegisterDto dto){
        Boolean isSuccess = null;
        Supplier personMethod = () -> commentService.addPersonComment(targetId, dto.toEntity());
        Supplier jobMethod = () -> commentService.addJobComment(targetId, dto.toEntity());
        isSuccess = checkTargetAndReturn(isSuccess, target, personMethod, jobMethod);
        log.info(target);


        return new BooleanResponseDto(isSuccess);
    }
    
    //댓글 조회
    @GetMapping("/api/v1/get/{commentId}")
    public CommentGetDto get(@PathVariable("commentId") Long commentId){

        Comment findComment = commentService.find(commentId);
        return CommentGetDto.toDto(findComment);
    }
    
    //댓글 목록 조회(페이징)
    @GetMapping("/api/v1/{target}/{targetId}/list")
    public CommentListDto getList(@PathVariable("target") String target
                                , @PathVariable("targetId") Long targetId,
                                  @RequestBody PageSortDto dto){

        List<Comment> findComments = null;
        Page page = dto.toPage();
        Supplier personMethod = () -> commentService.findAllByPerson(targetId, page, null);
        Supplier jobMethod = () -> commentService.findAllByJob(targetId, page, null);
        findComments = checkTargetAndReturn(findComments, target, personMethod, jobMethod);

        return CommentListDto.toDto(findComments);
    }

    private <T> T checkTargetAndReturn(T obj , String target, Supplier personMethod , Supplier jobMethod){

        if(target.equals(TARGET_PERSON)){
            obj = (T) personMethod.get();
        }
        else if(target.equals(TARGET_JOB)){
            obj = (T) jobMethod.get();
        } else {
            throw new IllegalArgumentException("target 이 적합하지 않습니다. target : " + target);
        }
        return obj;
    }
    
    //댓글 수정
    
    //댓글 삭제
    
    //좋아요
    
    //좋아요 취소

}
