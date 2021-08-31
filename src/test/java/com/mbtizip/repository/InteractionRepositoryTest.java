package com.mbtizip.repository;

import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.repository.interaction.InteractionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InteractionRepositoryTest {

    @Autowired
    private InteractionRepository interactionRepository;

    @Test
    public void Interaction_삽입(){

        //given
        String sessionId = "asdlskdj12312312";
        Long personId = 1L;
        String dType = "L";

        Interaction interaction = Interaction.builder()
                .sessionId(sessionId)
                .personId(personId)
                .dType(dType).build();

        //when
        interactionRepository.save(interaction);

        //then
        Interaction finded = interactionRepository.findOneByObject(interaction);

        assertEquals(finded.getSessionId() , sessionId );
        assertEquals(finded.getPersonId(), personId);
        assertEquals(finded.getDType(), dType);

    }

    @DisplayName("아무것도 찾지 못했을때 에러가 뜨는지에 대한 테스트")
    @Test
    public void 결과_0_에러(){

        //then
        assertThrows(IllegalArgumentException.class ,
                () -> interactionRepository.findOneByObject(Interaction.builder().build()));

        //when
        Interaction finded = interactionRepository.findOneByObject(Interaction.builder().dType("L").build());

        assertNull(finded);
        System.out.println("===========> " + finded);
    }
}
