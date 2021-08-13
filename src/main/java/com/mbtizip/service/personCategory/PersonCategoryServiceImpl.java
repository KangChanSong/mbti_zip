package com.mbtizip.service.personCategory;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonCategoryServiceImpl implements PersonCategoryService {

    private final PersonCategoryRepository personCategoryRepository;
    @Override
    public List<Category> findAllByPerson(Person person) {
        List<PersonCategory> personCategories = personCategoryRepository.findAllByPerson(person);
        return personCategories.stream().map(
                personCategory -> personCategory.getCategory()).collect(Collectors.toList());
    }

}
