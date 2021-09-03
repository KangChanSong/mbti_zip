package com.mbtizip.domain.mbti.dto;

import com.mbtizip.domain.mbti.Mbti;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
public class MbtiGetDto {
    private Long id;
    private String name;

    public static MbtiGetDto toDto(Mbti mbti){
        return MbtiGetDto.builder()
                .id(mbti.getId())
                .name(mbti.getName().getText())
                .build();
    }
}
