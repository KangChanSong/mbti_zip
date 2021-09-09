package com.mbtizip.domain.mbti.dto;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
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
                        mbtis.stream()
                                .map(mbti -> MbtiGetDto.toDto(mbti))
                                .filter(dto ->
                                        !dto.getName().equals(MbtiEnum.NONE.getText()) && !dto.getName().equals(MbtiEnum.DRAW.getText()))
                                .collect(Collectors.toList())
                ).build();

    }
}
