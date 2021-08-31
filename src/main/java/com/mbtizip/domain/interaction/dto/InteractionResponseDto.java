package com.mbtizip.domain.interaction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InteractionResponseDto {

    private boolean isAvailable;

    public InteractionResponseDto(boolean isSuccess){
        this.isAvailable = !isSuccess;
    }
}
