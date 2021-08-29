package com.mbtizip.controller.common.vote;

import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.mbtiCount.dto.MbtiCountListDto;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.mbtizip.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

import static com.mbtizip.controller.common.TargetProperties.TARGET_JOB;
import static com.mbtizip.controller.common.TargetProperties.TARGET_PERSON;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteApiController {

    private final JobService jobService;
    private final PersonService personService;
    private final MbtiCountService mbtiCountService;

    @PostMapping("/api/v1/mbti/{mbtiId}/{target}/{targetId}")
    public BooleanResponseDto vote(@PathVariable("mbtiId") Long mbtiId,
                                   @PathVariable("target") String target,
                                   @PathVariable("targetId") Long targetId){
        return new BooleanResponseDto(checkTargetAndExecute(
                () -> personService.vote(targetId,mbtiId),
                () -> jobService.vote(mbtiId, targetId),
                target));
    }

    @PostMapping("/api/v1/cancel/mbti/{mbtiId}/{target}/{targetId}")
    public BooleanResponseDto cancel(@PathVariable("mbtiId") Long mbtiId,
                                   @PathVariable("target") String target,
                                   @PathVariable("targetId") Long targetId){

        return new BooleanResponseDto(checkTargetAndExecute(
                () -> personService.cancelVote(targetId, mbtiId),
                () -> jobService.cancelVote(mbtiId, targetId),
                target));
    }

    @GetMapping("/api/v1/list/{target}/{targetId}")
    public MbtiCountListDto getList(@PathVariable("target") String target,
                                    @PathVariable("targetId") Long targetId){

        return MbtiCountListDto.toDto(checkTargetAndExecute(
                () -> mbtiCountService.getVotesByPerson(targetId),
                () -> mbtiCountService.getVotesByJob(targetId),
                target));
    }

    private <T> T checkTargetAndExecute(Supplier<T> personFunc, Supplier<T> jobFunc, String target) {

        if(target.equals(TARGET_PERSON)) return personFunc.get();
        else if (target.equals(TARGET_JOB)) return jobFunc.get();
        else throw new IllegalArgumentException("target 파라미터가 적합하지 않습니다. target : " + target );
    }
}
