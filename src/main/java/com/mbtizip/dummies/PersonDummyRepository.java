package com.mbtizip.dummies;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class PersonDummyRepository {

    private final EntityManager em;
    private final CategoryDummyRepository categoryDummyRepository;

    @Transactional
    public void save(Person person){
        em.persist(person);
    }

    @Transactional
    public void insertPersons(){

        Category category = categoryDummyRepository.getCategory();

        IntStream.range(0, 50).forEach(i -> {
            Person person = Person.builder()
                    .name("name" + i)
                    .description("description" + i)
                    .gender(Gender.MALE)
                    .writer("writer" + i)
                    .password("password" + i)
                    .build();

            save(person);
            person.setCategory(category);
        });
    }
}
