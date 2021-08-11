package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;

import java.util.List;

public interface PersonService {
    Long registerWithCategory(Person person, List<Category> categories);
    Person getById(Long saveId);
    List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);
    void delete(Person person);
}
