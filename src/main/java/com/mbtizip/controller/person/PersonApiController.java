package com.mbtizip.controller.person;

import com.mbtizip.domain.common.PageSortFilterDto;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.dto.PersonGetDto;
import com.mbtizip.domain.person.dto.PersonListDto;
import com.mbtizip.domain.person.dto.PersonRegisterDto;
import com.mbtizip.service.person.PersonService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonService personService;

    //인물 카테고리와 함께 등록
    @PostMapping("/api/v1/register")
    public PersonBooleanWrapper register(@RequestBody PersonRegisterDto dto){
        Person person = dto.toEntity();
        Boolean isSuccess = personService.registerWithCategory(person, dto.getCategoryIds());
        return new PersonBooleanWrapper(isSuccess);
    }

    //인물 조회 (MBTI, 카테고리 포함)
    @GetMapping("/api/v1/get/{personId}")
    public PersonGetDto get(@PathVariable("personId") Long personId){
        Person findPerson = personService.getById(personId);
        return PersonGetDto.toDto(findPerson);
    }

    // MBTI 에 해당하는 인물 목록 조회
    @GetMapping("/api/v1/list/mbti/{mbtiId}/{page}/{amount}/{sort}/{dir}")
    public PersonListDto getListByMbti(@PathVariable("mbtiId") Long id, @RequestBody PageSortFilterDto psf){

        return null;
    }

    //인물 수정

    //인물 삭제


    @Getter
    @Setter
    @AllArgsConstructor
    static class PersonBooleanWrapper{
        private Boolean isSuccess;
    }

}
