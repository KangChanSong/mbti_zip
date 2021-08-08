package com.mbtizip.repository;

import com.mbtizip.domain.Mbti;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MbtiRepositoryTest {

    @Autowired
    MbtiRepository mbtiRepository;

    @Test
    public void MBTI_등록_조회(){
        //given
        String name = "INFP";
        Mbti mbti = Mbti.builder()
                .name(name)
                .build();

        //when
        Long saveId = mbtiRepository.save(mbti);

        //then
        Mbti findMbti = mbtiRepository.find(saveId);

        assertEquals(findMbti.getName(), name);
    }

}