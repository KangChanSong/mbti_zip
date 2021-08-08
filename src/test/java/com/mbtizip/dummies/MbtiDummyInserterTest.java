package com.mbtizip.dummies;

import com.mbtizip.domain.mbti.Mbti;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MbtiDummyInserterTest {

    @Autowired
    EntityManager em;
    @Test
    @Transactional
    public void 더미_확인(){

        //then
        List<Mbti> mbtis = em.createQuery("select m from Mbti m").getResultList();

        assertEquals(mbtis.size(), 16);
    }

}