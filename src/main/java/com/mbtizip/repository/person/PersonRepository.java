package com.mbtizip.repository.person;

import com.mbtizip.domain.person.Person;

import java.util.List;

public interface PersonRepository {
    Long save(Person person);
    Person find(Long id);
    List<Person> getList();
    void modifyLikes(Person person, Boolean isIncrease);
}
