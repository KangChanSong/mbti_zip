package com.mbtizip.repository.test;

import com.mbtizip.domain.mbti.Mbti;

import javax.persistence.EntityManager;
import java.util.List;

public class TestMbtiRepository {

    private final EntityManager em;
    public TestMbtiRepository(EntityManager em){
        this.em = em;
    }

    public List<Mbti> findAll(){
        return em.createQuery("select m from Mbti m")
                .getResultList();
    }
}
