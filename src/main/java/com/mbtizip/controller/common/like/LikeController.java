package com.mbtizip.controller.common.like;

import com.mbtizip.controller.common.common.InteractionDType;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.service.comment.CommentService;
import com.mbtizip.service.interaction.InteractionService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import static com.mbtizip.controller.common.TargetProperties.*;
import static com.mbtizip.controller.common.common.InteractionControllerHelper.TARGET_INVALID_ERROR_MESSAGE;
import static com.mbtizip.controller.common.common.InteractionControllerHelper.buildInteraction;
import static com.mbtizip.controller.common.common.InteractionDType.L;

@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    private final PersonService personService;
    private final JobService jobService;
    private final CommentService commentService;
    private final InteractionService interactionService;


    @PostMapping("/api/v1/{target}/{targetId}")
    public BooleanResponseDto like(@PathVariable("target") String target,
                                   @PathVariable("targetId") Long targetId){
        boolean isExists = interactionService.checkIfExists(buildInteraction(target, targetId, L.name()));

        Boolean isSuccess;

        if(!isExists) {
            if (target.equals(TARGET_PERSON)) isSuccess = personService.like(targetId);
            else if (target.equals(TARGET_JOB)) isSuccess = jobService.like(targetId);
            else if (target.equals(TARGET_COMMENT)) isSuccess = commentService.like(targetId);
            else throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);
        } else {
            if(target.equals(TARGET_PERSON)) isSuccess = personService.cancelLike(targetId);
            else if(target.equals(TARGET_JOB)) isSuccess = jobService.cancelLike(targetId);
            else if(target.equals(TARGET_COMMENT)) isSuccess = commentService.cancelLike(targetId);
            else throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);
        }

        return new BooleanResponseDto(isSuccess);
    }

}
