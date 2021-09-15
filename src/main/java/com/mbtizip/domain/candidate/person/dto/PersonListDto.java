package com.mbtizip.domain.candidate.person.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PersonListDto {

    private List<PersonGetDto> personGetDtos;

    public static PersonListDto toDtoList(List<PersonGetDto> dtoList){
        return PersonListDto.builder()
                .personGetDtos(dtoList)
                .build();
    }
}
