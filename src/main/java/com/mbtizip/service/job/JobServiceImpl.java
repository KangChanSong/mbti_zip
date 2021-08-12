package com.mbtizip.service.job;

import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
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

    @Transactional
    @Override
    public Long register(Job job) {
        if(job == null) throw new IllegalArgumentException("Job 이 존재하지 않습니다.");
        return jobRepository.save(job);
    }

    @Override
    public Job get(Long id) {
        return jobRepository.find(id);
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

    @Override
    public void delete(Job job) {
        jobRepository.remove(job);
    }


}
