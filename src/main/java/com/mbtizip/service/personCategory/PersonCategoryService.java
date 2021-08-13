package com.mbtizip.service.personCategory;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface PersonCategoryService {
    List<Category> findAllByPerson(Person person);
}
