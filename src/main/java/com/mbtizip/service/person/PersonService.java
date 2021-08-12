package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;

import java.util.List;
import java.util.Map;

public interface PersonService {
    Boolean registerWithCategory(Person person, List<Long> categoryId);
    Person getById(Long saveId);
    Map<Person, List<Category>> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);
    Map<Person, List<Category>> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId);
    Boolean delete(Long id);
    Boolean vote(Long personId, Long mbtiId);
}
