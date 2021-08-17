package com.mbtizip.repository.person;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface PersonRepository {
    Long save(Person person);
    Person find(Long id);
    List<Person> findAll();
    List<Person> findAll(Page page);
    List<Person> findAll(Page page, OrderSpecifier sort);
    List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);
    void modifyLikes(Person person, Boolean isIncrease);
    void remove(Person person);
    Person findWithMbti(Long saveId);
}
