package com.mbtizip.dummies;

import com.mbtizip.domain.mbti.MbtiEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Transactional
public class DummyInserter {

    private final MbtiDummyRepository dummyRepository;

    @PostConstruct
    public void insertMbtis(){
        dummyRepository.save(MbtiEnum.INFJ);
        dummyRepository.save(MbtiEnum.INFP);
        dummyRepository.save(MbtiEnum.INTJ);
        dummyRepository.save(MbtiEnum.INTP);
        dummyRepository.save(MbtiEnum.ISFJ);
        dummyRepository.save(MbtiEnum.ISFP);
        dummyRepository.save(MbtiEnum.ISTJ);
        dummyRepository.save(MbtiEnum.ISTP);
        dummyRepository.save(MbtiEnum.ENFJ);
        dummyRepository.save(MbtiEnum.ENFP);
        dummyRepository.save(MbtiEnum.ENTJ);
        dummyRepository.save(MbtiEnum.ENTP);
        dummyRepository.save(MbtiEnum.ESFJ);
        dummyRepository.save(MbtiEnum.ESFP);
        dummyRepository.save(MbtiEnum.ESTJ);
        dummyRepository.save(MbtiEnum.ESTP);
    }
}
