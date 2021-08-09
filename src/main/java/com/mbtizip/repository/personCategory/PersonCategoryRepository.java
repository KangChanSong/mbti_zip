package com.mbtizip.repository.personCategory;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface PersonCategoryRepository {
    Long save(PersonCategory personCategory);

    List<PersonCategory> findAllByPerson(Person person);
    List<PersonCategory> findAllByCategory(Category category);
}
