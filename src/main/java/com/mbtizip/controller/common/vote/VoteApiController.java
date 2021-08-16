package com.mbtizip.controller.common.vote;

import com.mbtizip.controller.common.TargetProperties;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mbtizip.controller.common.TargetProperties.TARGET_JOB;
import static com.mbtizip.controller.common.TargetProperties.TARGET_PERSON;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteApiController {

    private final JobService jobService;
    private final PersonService personService;

    @PostMapping("/api/v1/mbti/{mbtiId}/{target}/{targetId}")
    public BooleanResponseDto vote(@PathVariable("mbtiId") Long mbtiId,
                                   @PathVariable("target") String target,
                                   @PathVariable("targetId") Long targetId){
        Boolean isSuccess;
             if(target.equals(TARGET_PERSON)) isSuccess = personService.vote(targetId, mbtiId);
        else if (target.equals(TARGET_JOB)) isSuccess = jobService.vote(mbtiId, targetId);
        else throw new IllegalArgumentException("target 파라미터가 적합하지 않습니다. target : " + target );

        return new BooleanResponseDto(isSuccess);
    }

    @PostMapping("/api/v1/cancel/mbti/{mbtiId}/{target}/{targetId}")
    public BooleanResponseDto cancel(@PathVariable("mbtiId") Long mbtiId,
                                   @PathVariable("target") String target,
                                   @PathVariable("targetId") Long targetId){
        Boolean isSuccess;
        if(target.equals(TARGET_PERSON)) isSuccess = personService.cancelVote(targetId, mbtiId);
        else if (target.equals(TARGET_JOB)) isSuccess = jobService.cancelVote(mbtiId, targetId);
        else throw new IllegalArgumentException("target 파라미터가 적합하지 않습니다. target : " + target );

        return new BooleanResponseDto(isSuccess);
    }
}
