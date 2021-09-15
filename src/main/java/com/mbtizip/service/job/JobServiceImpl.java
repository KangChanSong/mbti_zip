package com.mbtizip.service.job;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.service.file.FileService;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mbtizip.util.EncryptHelper.encrypt;
import static com.mbtizip.util.EncryptHelper.isMatch;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;
    private final MbtiRepository mbtiRepository;
    private final MbtiCountService mbtiCountService;
    private final FileService fileService;

    @Transactional
    @Override
    public Boolean register(Job job) {
        //테스트 가능
        if(job == null) throw new IllegalArgumentException("Job 이 존재하지 않습니다.");

        job.setPassword(encrypt(job.getPassword()));
        Long saveId = jobRepository.save(job);

        if(saveId != null){
            mbtiCountService.initializeByCandidate(job);
        }
        return saveId == null ? false : true;
    }

    @Transactional
    @Override
    public Job get(Long id) {
        Job findJob = checkAndReturn(id);
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
    public Boolean delete(Long jobId, String password) {
        Job findJob = jobRepository.find(jobId);
        if(findJob == null) throw new IllegalArgumentException("직업을 찾을 수 없습니다. id : " + jobId);
        if(isMatch(password, findJob.getPassword())) {
            delete(findJob);
            return true;
        }
        else{
            return false;
        }
    }

    private void delete(Job job){
        fileService.deleteFileWithCandidate(job);
        mbtiCountService.deleteAllByCandidate(job);
        jobRepository.remove(job);
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

    @Transactional
    @Override
    public Boolean like(Long jobId) {
        checkAndReturn(jobId).modifyLikes(true);
        return true;
    }

    @Transactional
    @Override
    public Boolean cancelLike(Long jobId) {
        checkAndReturn(jobId).modifyLikes(false);
        return true;
    }

    @Override
    public Long getTotalCount() {
        return jobRepository.countAll();
    }

    @Transactional
    @Override
    public void increaseView(Long jobId) {
        Job job = jobRepository.find(jobId);
        job.increaseViews();
    }

    @Override
    public Boolean checkIfExists(String title) {
        return jobRepository.countByTitle(title) > 0 ? true : false;
    }

    private Job checkAndReturn(Long jobId){
        Job findJob = jobRepository.find(jobId);
        if(findJob == null) throw new IllegalArgumentException("직업을 찾을 수 없습니다. id : " + jobId);
        return findJob;
    }


}
