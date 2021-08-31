package com.mbtizip.service.interaction;

import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.repository.interaction.InteractionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InteractionServiceTest {

    @Mock
    private InteractionRepository interactionRepository;

    private InteractionService interactionService;

    @BeforeEach
    public void setUp(){
        interactionService = new InteractionSeviceImpl(interactionRepository);
    }

    @DisplayName("아무것도 찾지 못했을때 false를 반환하는 테스트")
    @Test
    public void false_반환(){
        //given
        Interaction interaction = Interaction.builder().dType("L").build();
        when(interactionRepository.findOneByObject(interaction)).thenReturn(null);

        //when
        boolean isExists = interactionService.checkIfExists(interaction);
        //then
        assertFalse(isExists);
    }

    @DisplayName("좋아요 버튼을 누른 기록이 존재할때 true 를 반환하는 테스트")
    @Test
    public void true_반환(){

        //given
        Interaction interaction = Interaction.builder().dType("L").build();
        when(interactionRepository.findOneByObject(interaction)).thenReturn(interaction);

        //when
        boolean isExists = interactionService.checkIfExists(interaction);

        //then
        assertTrue(isExists);
    }

}
