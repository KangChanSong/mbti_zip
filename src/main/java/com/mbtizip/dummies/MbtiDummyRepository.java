package com.mbtizip.dummies;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MbtiDummyRepository {

    private final EntityManager em;

    @Transactional
    public void save(MbtiEnum mbtiEnum){
        Mbti mbti = Mbti.builder().name(mbtiEnum).build();
        em.persist(mbti);
    }

    public void insertMbtis(){
        save(MbtiEnum.INFP);
        save(MbtiEnum.INTJ);
        save(MbtiEnum.INTP);
        save(MbtiEnum.ISFJ);
        save(MbtiEnum.ISFP);
        save(MbtiEnum.ISTJ);
        save(MbtiEnum.INFJ);
        save(MbtiEnum.ISTP);
        save(MbtiEnum.ENFJ);
        save(MbtiEnum.ENFP);
        save(MbtiEnum.ENTJ);
        save(MbtiEnum.ENTP);
        save(MbtiEnum.ESFJ);
        save(MbtiEnum.ESFP);
        save(MbtiEnum.ESTJ);
        save(MbtiEnum.ESTP);
        save(MbtiEnum.NONE);
        save(MbtiEnum.DRAW);
    }
}
