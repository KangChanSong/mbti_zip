package com.mbtizip.dummies;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Getter
@Component
@RequiredArgsConstructor
@Transactional
public class DummyInserter {

    private final MbtiDummyRepository mbtiDummyRepository;
    private final CategoryDummyRepository categoryDummyRepository;
    private final PersonDummyRepository personDummyRepository;
    private final JobDummyRepository jobDummyRepository;
    private final MbtiCountDummyRepository mbtiCountDummyRepository;

    @Value("${insert.dummies}")
    private Boolean insertDummies;

    @PostConstruct
    public void insertDummies(){
        if(insertDummies) {
            mbtiDummyRepository.insertMbtis();
            categoryDummyRepository.insertCategoies();
            personDummyRepository.insertPersons();
            jobDummyRepository.insertJobs();
            mbtiCountDummyRepository.insertAll();
        }
    }
}
