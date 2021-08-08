package com.mbtizip.repository;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.mbti.MbtiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MbtiRepositoryTest {

    @Autowired
    MbtiRepository mbtiRepository;

    @Test
    public void MBTI_등록_조회(){
        //given
        Mbti mbti = Mbti.builder()
                .name(MbtiEnum.INFP)
                .build();

        //when
        Long saveId = mbtiRepository.save(mbti);

        //then
        Mbti findMbti = mbtiRepository.find(saveId);

        assertEquals(findMbti.getName(), MbtiEnum.INFP);
    }

    @Test
    public void 조회_실패_예외(){
        //then

        assertThrows(NoEntityFoundException.class, () -> {
            mbtiRepository.find(123L);
        });
    }

}