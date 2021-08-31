package com.mbtizip.controller.common.vote;

import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.domain.interaction.dto.InteractionResponseDto;
import com.mbtizip.domain.mbtiCount.dto.MbtiCountGetDto;
import com.mbtizip.domain.mbtiCount.dto.MbtiCountListDto;
import com.mbtizip.service.interaction.InteractionService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.mbtizip.service.person.PersonService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mbtizip.controller.common.common.InteractionControllerHelper.handleTarget;
import static com.mbtizip.controller.common.common.InteractionDType.V;

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

        boolean isExists = interactionService.checkIfExists(new Interaction(target, targetId, V.name()));

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

        MbtiCountListDto listDto = MbtiCountListDto.toDto(
                handleTarget(
                        target,
                        () -> mbtiCountService.getVotesByPerson(targetId),
                        () -> mbtiCountService.getVotesByJob(targetId)));

        return new VoteResponseDto(listDto, !isExists);
    }

    @Data
    private static class VoteResponseDto{

        private List<MbtiCountGetDto> mbtiCountGetDtos;
        private boolean isAvailable;

        VoteResponseDto(MbtiCountListDto listDto, boolean isAvailable){
            this.mbtiCountGetDtos = listDto.getMbtiCountGetDtos();
            this.isAvailable = isAvailable;
        }
    }

}
