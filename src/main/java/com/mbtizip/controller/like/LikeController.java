package com.mbtizip.controller.like;

import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.comment.CommentService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    public static final String TARGET_PERSON = "person";
    public static final String TARGET_JOB = "job";
    public static final String TARGET_COMMENT = "comment";

    private final PersonService personService;
    private final JobService jobService;
    private final CommentService commentService;

    @PostMapping("/api/v1/add/{target}/{targetId}")
    public BooleanResponseDto addLike(@PathVariable("target") String target,
                                      @PathVariable("targetId") Long targetId){
        Boolean isSuccess;
             if(target.equals(TARGET_PERSON)) isSuccess = personService.like(targetId);
        else if(target.equals(TARGET_JOB)) isSuccess = jobService.like(targetId);
        else if(target.equals(TARGET_COMMENT)) isSuccess = commentService.like(targetId);
        else throw new IllegalArgumentException("target 파라미터가 적합하지 않습니다.");

        return new BooleanResponseDto(isSuccess);
    }

    @PostMapping("/api/v1/cancel/{target}/{targetId}")
    public BooleanResponseDto cancelLike(@PathVariable("target") String target,
                                         @PathVariable("targetId") Long targetId){

        Boolean isSuccess;
             if(target.equals(TARGET_PERSON)) isSuccess = personService.cancelLike(targetId);
        else if(target.equals(TARGET_JOB)) isSuccess = jobService.cancelLike(targetId);
        else if(target.equals(TARGET_COMMENT)) isSuccess = commentService.cancelLike(targetId);
        else throw new IllegalArgumentException("target 파라미터가 적합하지 않습니다.");

        return new BooleanResponseDto(isSuccess);
    }
}
