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
    private final CategoryDummyRepository categoryDummyRepository;

    @PostConstruct
    public void insertDummies(){
        insertMbtis();
        insertCategoies();
    }

    private void insertMbtis(){
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

    private void insertCategoies(){
        
        String[] names = new String[]{
                "음악가","정치인","연예인","애니주인공","연기자", "개발자"
        };

        for(int i = 0 ; i < names.length ; i++){
            categoryDummyRepository.save(names[i]);
        }
    }
}
