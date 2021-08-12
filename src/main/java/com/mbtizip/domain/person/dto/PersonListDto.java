package com.mbtizip.domain.person.dto;

import com.mbtizip.domain.person.Person;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PersonListDto {

    private List<PersonGetDto> personGetDtos;

    public static PersonListDto toDtoList(List<Person> persons){
        return PersonListDto.builder()
                .personGetDtos(convertPersonList(persons))
                .build();
    }

    private static List<PersonGetDto> convertPersonList(List<Person> persons){
        return persons.stream().map( person -> PersonGetDto.toDto(person))
                .collect(Collectors.toList());
    }
}
