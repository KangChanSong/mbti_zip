package com.mbtizip.dummies;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
@RequiredArgsConstructor
public class PersonCategoryDummyRepository {

    @Autowired
    EntityManager em;

    public void insertPersonCategories(){
        // find Person
        List<Person> persons = findAllPersons();
        // find Category
        List<Category> categories = findAllCategories();
        // insert PersonCategories
        persons.forEach(person  -> insertPersonCategory(PersonCategory.builder()
                .person(person)
                .category(categories.get(0))
                .build()));
    }

    private void insertPersonCategory(PersonCategory personCategory){
        em.persist(personCategory);
    }

    private List<Person> findAllPersons(){
        return (List<Person>) em.createQuery("select p from Person p")
                .getResultList();
    }

    private List<Category> findAllCategories(){
        return (List<Category>) em.createQuery("select c from Category c")
                .getResultList();
    }

}
