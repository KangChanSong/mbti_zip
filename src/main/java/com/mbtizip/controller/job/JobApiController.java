package com.mbtizip.controller.job;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.pageSortFilter.PageSortFilterDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.dto.JobGetDto;
import com.mbtizip.domain.job.dto.JobListDto;
import com.mbtizip.domain.job.dto.JobRegisterDto;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobApiController {

    private final JobService jobService;
    
    //직업 등록
    @PostMapping("/api/v1/register")
    public BooleanResponseDto register(@RequestBody JobRegisterDto dto){

        Boolean isSuccess = jobService.register(dto.toEntity());

        return new BooleanResponseDto(isSuccess);
    }


    //직업 조회
    @GetMapping("/api/v1/get/{jobId}")
    public JobGetDto get(@PathVariable("jobId") Long id){

        Job job = jobService.get(id);
        return JobGetDto.toDto(job);
    }
    
    //직업 MBTI 투표
    @PostMapping("/api/v1/vote/{jobId}/mbti/{mbtiId}")
    public BooleanResponseDto vote(@PathVariable("jobId") Long jobId, @PathVariable("mbtiId") Long mbtiId){

        Boolean isSuccess = jobService.vote(mbtiId, jobId);
        return new BooleanResponseDto(isSuccess);
    }

    //직업 MBTI 투표 취소
    @PostMapping("/api/v1/cancel_vote/{jobId}/mbti/{mbtiId}")
    public BooleanResponseDto cancelVote(@PathVariable("jobId") Long jobId, @PathVariable("mbtiId") Long mbtiId){

        Boolean isSuccess = jobService.cancelVote(mbtiId, jobId);
        return new BooleanResponseDto(isSuccess);
    }

    //직업 MBTI로 목록 조회
    @GetMapping("/api/v1/list/mbti/{mbtiId}")
    public JobListDto getListWithMbti(@PathVariable("mbtiId") Long mbtiId, @RequestBody PageSortDto psd){
        Page page = psd.toPage();
        OrderSpecifier sort = psd.toJobSort();
        List<Job> findJobs = jobService.findAllWithMbti(page, sort, mbtiId);
        return JobListDto.toDto(findJobs);
    }

    //직업 목록 조회
    @GetMapping("/api/v1/list")
    public JobListDto getList(@RequestBody PageSortFilterDto psf){

        Page page = psf.toPage();
        OrderSpecifier sort = psf.toJobSort();
        BooleanExpression keyword = psf.toJobKeyword();

        List<Job> fnidJobs = jobService.findAll(page, sort, keyword);

        return JobListDto.toDto(fnidJobs);
    }

    //직업 삭제
    @DeleteMapping("/api/v1/delete/{jobId}")
    public BooleanResponseDto delete(@PathVariable("jobId") Long jobId){

        Boolean isSuccess = jobService.delete(jobId);
        return new BooleanResponseDto(isSuccess);
    }

    //좋아요
    @PostMapping("/api/v1/like/{jobId}")
    public BooleanResponseDto like(@PathVariable("jobId") Long jobId){
        Boolean isSuccess = jobService.like(jobId);
        return new BooleanResponseDto(isSuccess);
    }
    //좋아요 취소
    @PostMapping("/api/v1/cancel_like/{jobId}")
    public BooleanResponseDto cancelLike(@PathVariable("jobId") Long jobId){
        Boolean isSuccess = jobService.cancelLike(jobId);
        return new BooleanResponseDto(isSuccess);
    }


}