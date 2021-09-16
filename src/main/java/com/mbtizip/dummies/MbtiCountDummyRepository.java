package com.mbtizip.dummies;

import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MbtiCountDummyRepository {

    private final CandidateRepository candidateRepository;
    private final MbtiCountService mbtiCountService;

    @Transactional
    public void insertAll(){
        Page page = Page.builder().pageNum(1).amount(100).build();
        candidateRepository.findAll(Person.class, page).forEach(mbtiCountService::initializeByCandidate);
        candidateRepository.findAll(Job.class, page).forEach(mbtiCountService::initializeByCandidate);

    }
}
