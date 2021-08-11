package com.mbtizip.repository.person;

import com.mbtizip.domain.comment.Page;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface PersonRepository {
    Long save(Person person);
    Person find(Long id);
    List<Person> findAll();
    List<Person> findAllWithPaging(Page page);
    void modifyLikes(Person person, Boolean isIncrease);
    Person findWithMbti(Long saveId);
}
