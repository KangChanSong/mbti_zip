package com.mbtizip.repository;

import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.repository.interaction.InteractionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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

        //when
        Long saveId = interactionRepository.save(Interaction.builder()
                .personId(personId)
                .dType(dType).build());


        //then
        Long count = interactionRepository.countByCondition(personId, null, dType);

        Assertions.assertEquals(count , 1);

    }
}
