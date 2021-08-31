package com.mbtizip.controller.common.like;

import com.mbtizip.controller.common.common.InteractionDType;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.domain.interaction.dto.InteractionResponseDto;
import com.mbtizip.service.comment.CommentService;
import com.mbtizip.service.interaction.InteractionService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.function.Consumer;
import java.util.function.Supplier;

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
    public InteractionResponseDto like(@PathVariable("target") String target,
                                       @PathVariable("targetId") Long targetId){
        boolean isExists = interactionService.checkIfExists(buildInteraction(target, targetId, L.name()));

        Boolean isSuccess;

        if(!isExists) {
            isSuccess = handleTarget(target,
                    () -> personService.like(targetId),
                    () -> jobService.like(targetId),
                    () -> commentService.like(targetId));
        } else {
            isSuccess = handleTarget(target,
                    () -> personService.cancelLike(targetId),
                    () -> jobService.cancelLike(targetId),
                    () -> commentService.cancelLike(targetId));
        }

        if(isSuccess) return new InteractionResponseDto(isExists);
        else throw new RuntimeException();
    }

    @GetMapping("/api/v1/get/{target}/{targetId}")
    public Integer get(@PathVariable("target") String target,
                            @PathVariable("targetId") Long targetId){
        return handleTarget(target,
                () -> personService.getById(targetId).getLikes(),
                () -> jobService.get(targetId).getLikes(),
                null);
    }

    private <T> T handleTarget(String target,
                               Supplier<T> personMethod,
                               Supplier<T> jobMethod,
                               Supplier<T> commentMethod ){
        T obj;
        if (target.equals(TARGET_PERSON)) obj = personMethod.get();
        else if (target.equals(TARGET_JOB)) obj = jobMethod.get();
        else if (target.equals(TARGET_COMMENT)) obj = commentMethod.get();
        else throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);

        return obj;
    }


}
