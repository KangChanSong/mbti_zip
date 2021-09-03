package com.mbtizip.domain.mbtiCount.dto;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MbtiCountGetDto {
    private Long id;
    private String mbti;
    private int count;

    public static MbtiCountGetDto toDto(MbtiCount mbtiCount){
        return MbtiCountGetDto.builder()
                .id(mbtiCount.getId())
                .mbti(mbtiCount.getMbti()
                        .getName()
                        .getText()
                        .toUpperCase(Locale.ROOT))
                .count(mbtiCount.getCount())
                .build();
    }
}
