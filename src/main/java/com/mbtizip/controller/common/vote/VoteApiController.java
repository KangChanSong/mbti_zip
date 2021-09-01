package com.mbtizip.controller.common.vote;

import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.domain.mbtiCount.dto.MbtiCountGetDto;
import com.mbtizip.domain.mbtiCount.dto.MbtiCountListDto;
import com.mbtizip.service.interaction.InteractionService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.mbtizip.service.person.PersonService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mbtizip.controller.common.common.InteractionControllerHelper.TARGET_INVALID_ERROR_MESSAGE;
import static com.mbtizip.controller.common.common.InteractionControllerHelper.handleTarget;
import static com.mbtizip.controller.common.common.InteractionDType.V;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteApiController {

    private final JobService jobService;
    private final PersonService personService;
    private final MbtiCountService mbtiCountService;
    private final InteractionService interactionService;

    @PostMapping("/api/v1/mbti/{mbtiId}/{target}/{targetId}")
    public BooleanResponseDto vote(@PathVariable("mbtiId") Long mbtiId,
                                       @PathVariable("target") String target,
                                       @PathVariable("targetId") Long targetId){

        boolean isExists = interactionService.checkAndRemove(new Interaction(target, targetId, V.name()));

        Boolean isSuccess;
        if(!isExists){
            isSuccess = handleTarget(
                    target,
                    () -> personService.vote(targetId,mbtiId),
                    () -> jobService.vote(mbtiId, targetId));
        } else {
            isSuccess = handleTarget(
                    target,
                    () -> personService.cancelVote(targetId, mbtiId),
                    () -> jobService.cancelVote(mbtiId, targetId));
        }
        return new BooleanResponseDto(isSuccess);

    }

    @GetMapping("/api/v1/list/{target}/{targetId}")
    public VoteResponseDto getList(@PathVariable("target") String target,
                                    @PathVariable("targetId") Long targetId){

        boolean isExists = interactionService.checkIfExists(new Interaction(target, targetId, V.name()));
        log.info(Boolean.toString(isExists));
        MbtiCountListDto listDto = MbtiCountListDto.toDto(
                handleTarget(
                        target,
                        () -> mbtiCountService.getVotesByPerson(targetId),
                        () -> mbtiCountService.getVotesByJob(targetId)));

        return new VoteResponseDto(listDto, !isExists, getTotalCount(target, targetId));
    }

    private Long getTotalCount(String target, Long targetId) {
        if(target.equals("person")) return mbtiCountService.getTotalCountOfPerson(targetId);
        else if(target.equals("job")) return mbtiCountService.getTotalCountOfJob(targetId);
        else throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);
    }

    @Data
    private static class VoteResponseDto{

        private List<MbtiCountGetDto> mbtiCountGetDtos;
        private boolean isAvailable;
        private Long total;

        VoteResponseDto(MbtiCountListDto listDto, boolean isAvailable, Long total){
            this.mbtiCountGetDtos = listDto.getMbtiCountGetDtos();
            this.isAvailable = isAvailable;
            this.total = total;
        }
    }

}
