package com.mbtizip.controller.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.pageSortFilter.PageSortFilterDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.dto.PersonGetDto;
import com.mbtizip.domain.person.dto.PersonListDto;
import com.mbtizip.domain.person.dto.PersonRegisterDto;
import com.mbtizip.service.personCategory.PersonCategoryService;
import com.mbtizip.service.person.PersonService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonService personService;
    private final PersonCategoryService categoryService;
    //인물 카테고리와 함께 등록
    @PostMapping("/api/v1/register")
    public BooleanResponseDto register(@RequestBody PersonRegisterDto dto){
        Person person = dto.toEntity();
        Boolean isSuccess = personService.registerWithCategory(person, dto.getCategoryIds());
        return new BooleanResponseDto(isSuccess);
    }

    //인물 조회 (MBTI, 카테고리 포함)
    @GetMapping("/api/v1/get/{personId}")
    public PersonGetDto get(@PathVariable("personId") Long personId){
        Person persons = personService.getById(personId);
        List<Category> categories = categoryService.findAllByPerson(persons);
        return PersonGetDto.toDto(persons, categories);
    }
    
    // MBTI 투표
    @PostMapping("/api/v1/vote/{personId}/mbti/{mbtiId}")
    public BooleanResponseDto vote(@PathVariable("personId") Long personId, @PathVariable("mbtiId") Long mbtiId){

        Boolean isSuccess = personService.vote(personId, mbtiId);
        return new BooleanResponseDto(isSuccess);
    }
    
    //MBTI 투표 취소
    @PostMapping("/api/v1/cancel_vote/{personId}/mbti/{mbtiId}")
    public BooleanResponseDto cancelVote(@PathVariable("personId") Long personId, @PathVariable("mbtiId") Long mbtiId){

        Boolean isSuccess = personService.cancelVote(personId, mbtiId);
        return new BooleanResponseDto(isSuccess);
    }

    // MBTI 에 해당하는 인물 목록 조회
    @GetMapping("/api/v1/list/mbti/{mbtiId}")
    public PersonListDto getListByMbti(@PathVariable("mbtiId") Long mbtiId, @RequestBody PageSortDto psf){

        Page page = psf.toPage();
        OrderSpecifier sort = psf.toPersonSort();

        Map<Person, List<Category>> map = personService.findAllWithMbti(page, sort, mbtiId);
        return PersonListDto.toDtoList(map);
    }

    //인물 목록 조회
    @GetMapping("/api/v1/list")
    public PersonListDto getList(@RequestBody PageSortFilterDto psf){

        Page page = psf.toPage();
        OrderSpecifier sort = psf.toPersonSort();
        BooleanExpression keyword  = psf.toPersonKeyword();

        Map<Person, List<Category>> findMap = personService.findAll(page, sort, keyword);

        return PersonListDto.toDtoList(findMap);
    }

    //인물 삭제
    @DeleteMapping("/api/v1/delete/{personId}")
    public BooleanResponseDto delete(@PathVariable("personId") Long id){

        Boolean isSuccess = personService.delete(id);
        return new BooleanResponseDto(isSuccess);
    }

}
