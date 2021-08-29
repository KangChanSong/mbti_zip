package com.mbtizip.dummies;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MbtiCountDummyRepository {

    private final PersonRepository personRepository;
    private final JobRepository jobRepository;
    private final MbtiCountService mbtiCountService;

    @Transactional
    public void insertAll(){
        Page page = Page.builder().pageNum(1).amount(100).build();
        personRepository.findAll(page).forEach(mbtiCountService::initializeByPerson);
        jobRepository.findAll(page).forEach(mbtiCountService::initailizeByJob);
    }
}
