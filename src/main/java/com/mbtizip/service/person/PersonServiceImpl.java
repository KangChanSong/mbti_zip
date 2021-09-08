package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.dto.PersonGetDto;
import com.mbtizip.repository.category.CategoryRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.service.file.FileService;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.mbtizip.util.EncryptHelper.encrypt;
import static com.mbtizip.util.EncryptHelper.isMatch;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final CategoryRepository categoryRepository;
    private final MbtiRepository mbtiRepository;
    private final MbtiCountService mbtiCountService;
    private final FileService fileService;


    @Transactional
    @Override
    public Boolean registerWithCategory(Person person, Long categoryId) {
        if(person == null) throw new IllegalArgumentException("Person 이 null 입니다.");
        person.setPassword(encrypt(person.getPassword()));
        Long saveId = personRepository.save(person);
        savePersonCategories(person, categoryId);

        if(saveId != null){
            mbtiCountService.initializeByPerson(person);
        }
        return saveId == null ? false : true;
    }

    @Override
    public Person getById(Long saveId) {
        Person findPerson = personRepository.find(saveId);
        if (findPerson == null) throw new IllegalArgumentException("Person 을 찾을 수 없습니다. id : " + saveId);
        return findPerson;
    }

    @Override
    public List<PersonGetDto> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {

        List<Person> findPersons;
        if(keyword == null){
            findPersons = personRepository.findAll(page, sort);
        } else {
            findPersons = personRepository.findAll(page, sort, keyword);
        }
        return  createPersonListWithCategories(findPersons);
    }



    @Override
    public List<PersonGetDto> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId) {
        MbtiEnum mbti = mbtiRepository.find(mbtiId).getName();
        BooleanExpression keyword = QMbti.mbti.name.eq(mbti);
        List<Person> findPersons = personRepository.findAll(page, sort, keyword);
        return createPersonListWithCategories(findPersons);
    }

    @Transactional
    @Override
    public Boolean delete(Long id, String password) {

        Person findPerson = checkAndReturnPerson(id);
        if(isMatch(password, findPerson.getPassword())) {
            delete(findPerson);
            return true;
        } else {
            return false;
        }
    }

    private void delete(Person person){
        fileService.deleteFileByPerson(person);
        mbtiCountService.deleteAllByPerson(person);
        personRepository.remove(person);
    }

    @Transactional
    @Override
    public Boolean vote(Long personId, Long mbtiId) {
        Mbti mbti = mbtiRepository.find(mbtiId);
        Person person = personRepository.find(personId);
        checkIfNull(mbti, person);

        mbtiCountService.vote(mbti, person);

        return true;
    }

    @Transactional
    @Override
    public Boolean cancelVote(Long personId, Long mbtiId) {
        Mbti mbti = mbtiRepository.find(mbtiId);
        Person person = personRepository.find(personId);

        mbtiCountService.cancelVote(mbti, person);

        return true;
    }

    @Transactional
    @Override
    public Boolean like(Long personId) {
        checkAndReturnPerson(personId).modifyLikes(true);
        return true;
    }

    @Transactional
    @Override
    public Boolean cancelLike(Long personId) {
        checkAndReturnPerson(personId).modifyLikes(false);
        return true;
    }

    @Transactional
    @Override
    public Boolean increaseView(Long personId) {
        checkAndReturnPerson(personId).increaseViews();
        return true;
    }

    @Override
    public Long getTotalCount() {
        return personRepository.countAll();
    }

    @Override
    public Boolean checkIfExists(String name) {
        return personRepository.countByName(name) > 0 ? true : false;
    }


    //== private method ==//
    private void savePersonCategories(Person person, Long categoryId){
        Category category = categoryRepository.find(categoryId);
        if(category == null) throw new IllegalArgumentException("카테고리를 찾을 수 없습니다. id : " + categoryId);
        person.setCategory(category);
    }


    private Person checkAndReturnPerson(Long personId){
        Person findPerson = personRepository.find(personId);
        if(findPerson == null) throw new IllegalArgumentException("인물을 찾을 수 없습니다.");
        return findPerson;
    }

    private List<PersonGetDto> createPersonListWithCategories(List<Person> findPersons) {
        List<PersonGetDto> dtoList = new ArrayList<>();
        findPersons.forEach(person -> dtoList.add(PersonGetDto.toDto(person)));
        return dtoList;
    }

    private void checkIfNull(Mbti mbti, Person person){
        if(mbti == null) throw new IllegalArgumentException(" MBTI가 존재하지 않습니다.");
        if(person == null) throw new IllegalArgumentException(" Person이 존재하지 않습니다.");
    }
}
