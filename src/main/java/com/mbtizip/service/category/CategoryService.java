package com.mbtizip.service.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface CategoryService {
    List<Category> findAllByPerson(Person person);
}
