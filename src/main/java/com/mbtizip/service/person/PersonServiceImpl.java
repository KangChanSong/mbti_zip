package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.repository.category.CategoryRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final PersonCategoryRepository personCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Boolean registerWithCategory(Person person, List<Long> categoryIds) {

        Long saveId = personRepository.save(person);
        categoryIds.forEach( categoryId -> {
            Category category = categoryRepository.find(categoryId);

            if(category == null) throw new IllegalArgumentException("카테고리를 찾을 수 없습니다. id : " + categoryId);

            PersonCategory personCategory = PersonCategory.builder()
                                    .person(person)
                                    .category(category).build();

            personCategoryRepository.save(personCategory);
        });

        return saveId == null ? false : true;
    }

    @Override
    public Person getById(Long saveId) {
        Person findPerson = personRepository.findWithMbti(saveId);
        return findPerson;
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {

        if(page == null){
            page = Page.builder().pageNum(1).amount(10).build();
        }
        if(sort == null){
            sort = QPerson.person.createDate.desc();
        }
        if(keyword == null){
            return personRepository.findAll(page, sort);
        }

        return personRepository.findAll(page, sort, keyword);
    }

    @Transactional
    @Override
    public Boolean delete(Person person) {

        personRepository.remove(person);
        return true;
    }
}
