package com.mbtizip.repository.mbti;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.exception.NoEntityFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MbtiRepositoryImpl implements MbtiRepository{

    private final EntityManager em;

    @Override
    public Long save(Mbti mbti){
        em.persist(mbti);
        return mbti.getId();
    }

    @Override
    public Mbti find(Long id){
        Mbti mbti = em.find(Mbti.class, id);
        if(mbti == null) throw new NoEntityFoundException("MBTI 엔티티가 존재하지 않습니다. id = " + id);
        return mbti;
    }
}
