package com.mbtizip.repository;

import com.mbtizip.controller.common.common.InteractionDType;
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

import static com.mbtizip.controller.common.common.InteractionDType.*;
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
        Long personId = 1L;
        String dType = "L";

        Interaction interaction = new Interaction("person", personId , dType);

        //when
        interactionRepository.save(interaction);

        //then
        Interaction finded = interactionRepository.findOneByObject(interaction);

        assertEquals(finded.getPersonId(), personId);
        assertEquals(finded.getDType(), dType);

    }

    @DisplayName("아무것도 찾지 못했을때 에러가 뜨는지에 대한 테스트")
    @Test
    public void 결과_0_에러(){
        //when
        assertThrows(IllegalArgumentException.class, () ->
                interactionRepository.findOneByObject(new Interaction("sd", 1L ,  "person")));
    }

    @DisplayName("Job에 대해 insert 했을 때 올바르게 찾는지에 대한 테스트")
    @Test
    public void JOB_INSERT(){

        //given
        Long jobId = 1L;
        String dType = V.name();

        Interaction interaction = new Interaction("job", jobId, dType);
        interactionRepository.save(interaction);
        //when

        Interaction finded = interactionRepository.findOneByObject(interaction);
        //then
        assertNotNull(finded);
        assertEquals(finded.getJobId(), jobId);
        assertEquals(finded.getDType(), dType);
    }
}
