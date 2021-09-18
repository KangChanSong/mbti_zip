package com.mbtizip.controller.common.like;

import com.mbtizip.controller.common.common.InteractionControllerHelper;
import com.mbtizip.controller.common.common.InteractionDType;
import com.mbtizip.controller.cookie.CookieController;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.domain.interaction.dto.InteractionResponseDto;
import com.mbtizip.service.comment.CommentService;
import com.mbtizip.service.interaction.InteractionService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.person.PersonService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.mbtizip.controller.common.TargetProperties.*;
import static com.mbtizip.controller.common.common.InteractionControllerHelper.*;
import static com.mbtizip.controller.common.common.InteractionDType.L;
import static com.mbtizip.controller.cookie.CookieController.INTERACTION_COOKIE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like")
public class LikeApiController {

    private final PersonService personService;
    private final JobService jobService;
    private final InteractionService interactionService;


    @PostMapping("/{target}/{targetId}")
    public BooleanResponseDto like(@PathVariable("target") String target,
                                   @PathVariable("targetId") Long targetId,
                                   @CookieValue(INTERACTION_COOKIE) String cookie){
        boolean isExists = interactionService.checkAndRemove(new Interaction(target, targetId, L.name(), cookie));

        Boolean isSuccess;

        if(!isExists) {
            isSuccess = handleTarget(target,
                    () -> personService.like(targetId),
                    () -> jobService.like(targetId));
        } else {
            isSuccess = handleTarget(target,
                    () -> personService.cancelLike(targetId),
                    () -> jobService.cancelLike(targetId));
        }

        return new BooleanResponseDto(isSuccess);
    }

    @GetMapping("/get/{target}/{targetId}")
    public LikeResponseDto get(@PathVariable("target") String target,
                            @PathVariable("targetId") Long targetId,
                               @CookieValue(INTERACTION_COOKIE) String cookie){

        log.info("좋아요 수 조회");

        int likes = handleTarget(target,
                () -> personService.getById(targetId).getLikes(),
                () -> jobService.get(targetId).getLikes());

        boolean isExists = interactionService.checkIfExists(new Interaction(target, targetId, L.name(), cookie));

        return new LikeResponseDto(likes, !isExists);
    }

    @Data
    private static class LikeResponseDto{
        private int likes;
        private boolean isAvailable;

        LikeResponseDto(int likes, boolean isAvailable){
            this.likes = likes;
            this.isAvailable = isAvailable;
        }
    }
}
