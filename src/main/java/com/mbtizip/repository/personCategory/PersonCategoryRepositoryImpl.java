package com.mbtizip.repository.personCategory;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.common.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonCategoryRepositoryImpl implements PersonCategoryRepository {

    private final EntityManager em;

    @Override
    public Long save(PersonCategory personCategory) {
        em.persist(personCategory);
        return personCategory.getId();
    }

    @Override
    public List<PersonCategory> findAllByPerson(Person person) {
        return (List<PersonCategory>) CommonRepository.findAllByObject(
                em, PersonCategory.class, Person.class, person.getId());
    }

    @Override
    public List<PersonCategory> findAllByCategory(Category category) {
        return (List<PersonCategory>) CommonRepository.findAllByObject(
                em, PersonCategory.class, Category.class, category.getId());
    }
}
