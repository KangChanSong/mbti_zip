package com.mbtizip.service.job;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;
    private final MbtiRepository mbtiRepository;
    private final MbtiCountService mbtiCountService;

    @Transactional
    @Override
    public Boolean register(Job job) {
        if(job == null) throw new IllegalArgumentException("Job 이 존재하지 않습니다.");
        return jobRepository.save(job) == null ? false : true;
    }

    @Override
    public Job get(Long id) {

        Job findJob = jobRepository.find(id);
        if(findJob == null) throw new IllegalArgumentException("Job 을 찾을 수 없습니다. id : " + id);
        return findJob;
    }

    @Override
    public List<Job> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        if(page == null){
            page = Page.builder().pageNum(1).amount(10).build();
        }
        if(sort == null){
            sort = QJob.job.createDate.desc();
        }
        if(keyword == null){
            return jobRepository.findAll(page, sort);
        }

        return jobRepository.findAll(page, sort, keyword);
    }

    @Override
    public List<Job> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId) {
        MbtiEnum mbti = mbtiRepository.find(mbtiId).getName();
        BooleanExpression keyword = QJob.job.mbti.name.eq(mbti);
        return jobRepository.findAll(page, sort, keyword);
    }


    @Transactional
    @Override
    public Boolean delete(Job job) {
        jobRepository.remove(job);
        return true;
    }

    @Transactional
    @Override
    public Boolean vote(Long mbtiId, Long jobId) {
        Mbti mbti = mbtiRepository.find(mbtiId);
        Job job = jobRepository.find(jobId);
        mbtiCountService.vote(mbti, job);
        return true;
    }

    @Transactional
    @Override
    public Boolean cancelVote(Long mbtiId, Long jobId) {
        Mbti mbti = mbtiRepository.find(mbtiId);
        Job job = jobRepository.find(jobId);
        mbtiCountService.cancelVote(mbti, job);
        return true;
    }


}
