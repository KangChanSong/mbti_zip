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

    public static PersonListDto toDtoList(Map<Person, List<Category>> map){
        return PersonListDto.builder()
                .personGetDtos(convertPersonList(map))
                .build();
    }

    private static List<PersonGetDto> convertPersonList(Map map){
        Set<Person> personSet = map.keySet();

        return personSet.stream().map(
                person -> PersonGetDto.toDto(person, (List<Category>) map.get(person)))
                                .collect(Collectors.toList());
    }
}
