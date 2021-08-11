package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface PersonService {
    Long registerWithCategory(Person person, List<Category> categories);

    Person getById(Long saveId);
}
