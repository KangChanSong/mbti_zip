package com.mbtizip.controller.job;

import com.mbtizip.domain.common.dto.CountDto;
import com.mbtizip.domain.common.dto.PasswordDto;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.pageSortFilter.PageSortFilterDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.dto.JobGetDto;
import com.mbtizip.domain.job.dto.JobListDto;
import com.mbtizip.domain.job.dto.JobRegisterDto;
import com.mbtizip.service.file.FileService;
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
    private final FileService fileService;
    
    //직업 등록
    @PostMapping("/api/v1/register")
    public BooleanResponseDto register(@RequestBody JobRegisterDto dto){

        Job job= dto.toEntity();
        Boolean isSuccess = jobService.register(job);
        fileService.saveFileWithJob(job.getId(), dto.getFilename());
        return new BooleanResponseDto(isSuccess);
    }

    //직업 조회
    @GetMapping("/api/v1/get/{jobId}")
    public JobGetDto get(@PathVariable("jobId") Long id){

        Job job = jobService.get(id);
        return JobGetDto.toDto(job);
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
    //직업 삭제

    @GetMapping("/api/v1/list")
    public JobListDto getList(@RequestParam(name ="page", required = false) int page,
                              @RequestParam(name ="size", required = false) int size,
                              @RequestParam(name ="sort", required = false) String sort,
                              @RequestParam(name ="dir", required = false) String dir,
                              @RequestParam(name ="keyword", required = false) String keyword,
                              @RequestParam(name ="filterBy", required = false) String filterBy){

        PageSortFilterDto psf = new PageSortFilterDto();
        psf.setPage(page);
        psf.setSize(size);
        psf.setSort(sort);
        psf.setDir(dir);
        psf.setKeyword(keyword);
        psf.setFilterBy(filterBy);

        Page pageObj = psf.toPage();
        OrderSpecifier sortObj = psf.toJobSort();
        BooleanExpression keywordObj = psf.toJobKeyword();

        List<Job> fnidJobs = jobService.findAll(pageObj, sortObj, keywordObj);

        return JobListDto.toDto(fnidJobs);
    }
    @DeleteMapping("/api/v1/delete/{jobId}")
    public BooleanResponseDto delete(@PathVariable("jobId") Long jobId, @RequestBody PasswordDto dto){
        Boolean isSuccess = jobService.delete(jobId, dto.getPassword());
        return new BooleanResponseDto(isSuccess);
    }

    @GetMapping("/api/v1/count/all")
    public CountDto getTotalCount(){
        return new CountDto(jobService.getTotalCount());
    }
}
