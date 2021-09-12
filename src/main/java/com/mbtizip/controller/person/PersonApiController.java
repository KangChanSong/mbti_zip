package com.mbtizip.controller.person;

import com.mbtizip.domain.common.dto.CountDto;
import com.mbtizip.domain.common.dto.PasswordDto;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.pageSortFilter.PageSortFilterDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.dto.PersonGetDto;
import com.mbtizip.domain.person.dto.PersonListDto;
import com.mbtizip.domain.person.dto.PersonRegisterDto;
import com.mbtizip.service.file.FileService;
import com.mbtizip.service.person.PersonService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonApiController {

    private final PersonService personService;
    private final FileService fileService;


    //인물 카테고리와 함께 등록
    @PostMapping("/register")
    public BooleanResponseDto register(@RequestBody PersonRegisterDto dto){
        Person person = dto.toEntity();
        Boolean isSuccess = personService.registerWithCategory(person, dto.getCategoryId());
        fileService.saveFileWithPerson(person.getId(), dto.getFilename());
        return new BooleanResponseDto(isSuccess);
    }

    //인물 조회 (MBTI, 카테고리 포함)
    @GetMapping("/get/{personId}")
    public PersonGetDto get(@PathVariable("personId") Long personId){
        log.info("Person 조회");
        Person person = personService.getById(personId);
        personService.increaseView(person.getId());
        return PersonGetDto.toDto(person);
    }

    // MBTI 에 해당하는 인물 목록 조회
    @GetMapping("/list/mbti/{mbtiId}")
    public PersonListDto getListByMbti(@PathVariable("mbtiId") Long mbtiId, @RequestBody PageSortDto psf){

        Page page = psf.toPage();
        OrderSpecifier sort = psf.toPersonSort();
        return PersonListDto.toDtoList(
                personService.findAllWithMbti(page, sort, mbtiId));
    }

    //인물 목록 조회
    @GetMapping("/list")
    public PersonListDto getList(@RequestParam(name = "page", required = false) int page,
                                 @RequestParam(name = "size", required = false) int size,
                                 @RequestParam(name = "sort", required = false) String sort,
                                 @RequestParam(name = "dir", required = false) String dir,
                                 @RequestParam(name = "keyword", required = false) String keyword,
                                 @RequestParam(name = "filterBy", required = false) String filterBy){
        log.info("인물 목록 조회");
        log.info("page : " + page +  " , size : " + size + ", sort : " + sort + ", dir : " + dir + ", keyword : " + keyword + ", filterBy : " + filterBy );
        PageSortFilterDto psf = new PageSortFilterDto();
        psf.setPage(page);
        psf.setSize(size);
        psf.setSort(sort);
        psf.setDir(dir);
        psf.setKeyword(keyword);
        psf.setFilterBy(filterBy);

        Page pageObj = psf.toPage();
        OrderSpecifier sortObj = psf.toPersonSort();
        BooleanExpression keywordObj  = psf.toPersonKeyword();

        return PersonListDto.toDtoList(personService.findAll(pageObj, sortObj, keywordObj));
    }

    //인물 삭제
    @DeleteMapping("/delete/{personId}")
    public BooleanResponseDto delete(@PathVariable("personId") Long id, @RequestBody PasswordDto dto){
        Boolean isSuccess = personService.delete(id, dto.getPassword());
        return new BooleanResponseDto(isSuccess);
    }

    @GetMapping("/count/all")
    public CountDto getTotalCount(){
        return new CountDto(personService.getTotalCount());
    }

    @GetMapping("/exists/{name}")
    public boolean checkIfExists(@PathVariable("name") String name){
        return personService.checkIfExists(name);
    }
}
