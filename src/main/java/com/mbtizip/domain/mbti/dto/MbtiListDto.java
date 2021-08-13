package com.mbtizip.domain.mbti.dto;

import com.mbtizip.domain.mbti.Mbti;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class MbtiListDto {

    private List<MbtiGetDto> mbtiGetDtos;

    public static MbtiListDto toDto(List<Mbti> mbtis){
        return MbtiListDto.builder()
                .mbtiGetDtos(
                        mbtis.stream().map(mbti -> MbtiGetDto.toDto(mbti))
                                .collect(Collectors.toList())
                ).build();

    }
}
