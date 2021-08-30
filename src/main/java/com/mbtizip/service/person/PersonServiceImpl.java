package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.repository.category.CategoryRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mbtizip.util.EncryptHelper.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final PersonCategoryRepository personCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MbtiRepository mbtiRepository;
    private final MbtiCountService mbtiCountService;

    @Transactional
    @Override
    public Boolean registerWithCategory(Person person, List<Long> categoryIds) {
        if(person == null) throw new IllegalArgumentException("Person 이 null 입니다.");
        person.setPassword(encrypt(person.getPassword()));
        Long saveId = personRepository.save(person);
        savePersonCategories(person, categoryIds);

        if(saveId != null){
            mbtiCountService.initializeByPerson(person);
        }
        return saveId == null ? false : true;
    }


    @Transactional
    @Override
    public Person getById(Long saveId) {
        Person findPerson = personRepository.findWithMbti(saveId);
        if(findPerson == null) throw new IllegalArgumentException("Person 을 찾을 수 없습니다. id : " + saveId);
        findPerson.increaseViews();
        return findPerson;
    }

    @Override
    public Map<Person, List<Category>> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {

        List<Person> findPersons;
        if(keyword == null){
            findPersons = personRepository.findAll(page, sort);
        } else {
            findPersons = personRepository.findAll(page, sort, keyword);
        }
        return  createPersonMapWithCategories(findPersons);
    }



    @Override
    public Map<Person, List<Category>> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId) {
        MbtiEnum mbti = mbtiRepository.find(mbtiId).getName();
        BooleanExpression keyword = QMbti.mbti.name.eq(mbti);
        List<Person> findPersons = personRepository.findAll(page, sort, keyword);
        return createPersonMapWithCategories(findPersons);
    }


    @Transactional
    @Override
    public Boolean delete(Long id, String password) {
        Person findPerson = checkAndReturnPerson(id);
        log.info(password);
        log.info(findPerson.getPassword());
        if(isMatch(password, findPerson.getPassword())){
            personRepository.remove(findPerson);
            return true;
        } else {
            return false;
        }
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

    @Override
    public Boolean increaseView(Long personId) {
        checkAndReturnPerson(personId).increaseViews();
        return true;
    }

    @Override
    public Long getTotalCount() {
        return personRepository.countAll();
    }

    //== private method ==//
    private void savePersonCategories(Person person, List<Long> categoryIds){
        categoryIds.forEach( categoryId -> {
            Category category = categoryRepository.find(categoryId);

            if(category == null) throw new IllegalArgumentException("카테고리를 찾을 수 없습니다. id : " + categoryId);

            PersonCategory personCategory = PersonCategory.builder()
                    .person(person)
                    .category(category).build();

            personCategoryRepository.save(personCategory);
        });
    }


    private Person checkAndReturnPerson(Long personId){
        Person findPerson = personRepository.find(personId);
        if(findPerson == null) throw new IllegalArgumentException("인물을 찾을 수 없습니다.");
        return findPerson;
    }

    private Map<Person, List<Category>> createPersonMapWithCategories(List<Person> findPersons) {
        Map<Person, List<Category>> map = new HashMap<>();

        findPersons.forEach(person -> {
            if(person.getPersonCategories() == null
                    || person.getPersonCategories().size()==0){
                map.put(person, null);
            } else {
                person.getPersonCategories().forEach(
                        personCategory -> {
                            List<Category> categories = new ArrayList<>();
                            categories.add(personCategory.getCategory());
                            map.put(person, categories);
                        }
                );
            }
        });
        return map;
    }

    private void checkIfNull(Mbti mbti, Person person){
        if(mbti == null) throw new IllegalArgumentException(" MBTI가 존재하지 않습니다.");
        if(person == null) throw new IllegalArgumentException(" Person이 존재하지 않습니다.");
    }
}
