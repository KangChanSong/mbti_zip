package com.mbtizip.controller.comment;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.comment.dto.CommentGetDto;
import com.mbtizip.domain.comment.dto.CommentListDto;
import com.mbtizip.domain.comment.dto.CommentRegisterDto;
import com.mbtizip.domain.comment.dto.CommentUpdateDto;
import com.mbtizip.domain.common.dto.CountDto;
import com.mbtizip.domain.common.dto.PasswordDto;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

import static com.mbtizip.controller.common.TargetProperties.TARGET_JOB;
import static com.mbtizip.controller.common.TargetProperties.TARGET_PERSON;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Slf4j
public class CommentApiController {


    private final CommentService commentService;

    /**
     * @param target : 댓글이 달릴 대상 person or job
     * @param targetId : 댓글이 달릴 대상의 id personId or jobId
     */
    //댓글 등록
    @PostMapping("/{target}/{targetId}/register")
    public BooleanResponseDto register(@PathVariable("target") String target,
                                       @PathVariable("targetId") Long targetId,
                                       @RequestBody CommentRegisterDto dto){

        return new BooleanResponseDto(commentService.addComment(targetId, dto.toEntity()));
    }
    
    //댓글 조회
    @GetMapping("/get/{commentId}")
    public CommentGetDto get(@PathVariable("commentId") Long commentId){

        Comment findComment = commentService.find(commentId);
        return CommentGetDto.toDto(findComment);
    }
    
    //댓글 목록 조회(페이징)
    @GetMapping("/{target}/{targetId}/list")
    public CommentListDto getList(@PathVariable("target") String target
                                , @PathVariable("targetId") Long targetId,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size,
                                  @RequestParam("sort") String sort,
                                  @RequestParam("dir") String dir){

        PageSortDto dto = new PageSortDto();
        dto.setPage(page);
        dto.setSize(size);
        dto.setSort(sort);
        dto.setDir(dir);

        Page pageObj = dto.toPage();

        return CommentListDto.toDto(commentService.findAll(targetId, pageObj, dto.toCommentSort()));
    }

    //댓글 수정
    @PutMapping("/update/{commentId}")
    public BooleanResponseDto update(@PathVariable("commentId") Long commentId,
                                     @RequestBody CommentUpdateDto dto){

        Boolean isSuccess = commentService.update(commentId, dto.toEntity());
        return new BooleanResponseDto(isSuccess);
    }
    
    //댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public BooleanResponseDto delete(@PathVariable("commentId") Long commentId, @RequestBody PasswordDto dto){

        Boolean isSuccess = commentService.delete(commentId, dto.getPassword());
        return new BooleanResponseDto(isSuccess);
    }

    @GetMapping("/{target}/{targetId}/count/all")
    public CountDto getTotalCount(@PathVariable("target") String target, @PathVariable("targetId") Long targetId){
        if(!target.equals("person") && !target.equals("job")){
            throw new IllegalArgumentException("target 이 적합하지 않습니다. target : " + target);
        }
        return new CountDto(commentService.getTotalCount(targetId));
    }

}
