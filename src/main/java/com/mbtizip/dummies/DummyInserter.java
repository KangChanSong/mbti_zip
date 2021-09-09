package com.mbtizip.dummies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Transactional
public class DummyInserter {

    private final MbtiDummyRepository mbtiDummyRepository;
    private final CategoryDummyRepository categoryDummyRepository;
    private final PersonDummyRepository personDummyRepository;
    private final JobDummyRepository jobDummyRepository;
    private final MbtiCountDummyRepository mbtiCountDummyRepository;

    //@PostConstruct
    public void insertDummies(){
        mbtiDummyRepository.insertMbtis();
        categoryDummyRepository.insertCategoies();
        personDummyRepository.insertPersons();
        jobDummyRepository.insertJobs();
        mbtiCountDummyRepository.insertAll();
    }
}
