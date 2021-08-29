package com.mbtizip.domain.mbtiCount.dto;

import com.mbtizip.domain.mbtiCount.MbtiCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MbtiCountListDto {

    private int total;
    private List<MbtiCountGetDto> mbtiCountGetDtos;

    public static MbtiCountListDto toDto(List<MbtiCount> mbtiCounts){

        return MbtiCountListDto.builder()
                .total(getVoteCounts(mbtiCounts))
                .mbtiCountGetDtos(
                        mbtiCounts.stream().map(MbtiCountGetDto::toDto)
                                .collect(Collectors.toList()))
                .build();
    }

    private static int getVoteCounts(List<MbtiCount> mbtiCounts) {

        int total = 0;

        for (MbtiCount m: mbtiCounts) {
            total += m.getCount();
        }

        return total;
    }

}
