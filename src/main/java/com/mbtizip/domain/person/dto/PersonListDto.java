package com.mbtizip.domain.person.dto;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Person;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
