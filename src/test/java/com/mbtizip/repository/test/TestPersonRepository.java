package com.mbtizip.repository.test;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.candidate.person.Person;

import javax.persistence.EntityManager;

public class TestPersonRepository {
    public static final String PERSON_NAME ="송강찬";

    EntityManager em;

    public TestPersonRepository(EntityManager em) {
        this.em = em;
    }

    public Person createPerson(){
        Person person = Person.builder().name(PERSON_NAME).build();
        em.persist(person);
        return person;
    }

    public Person createPerson(String name){
        Person person = Person.builder().name(name).build();
        em.persist(person);
        return person;
    }

    public Person createPersonWithMbti(Mbti mbti){
        Person person = Person.builder()
                .name(PERSON_NAME).build();
        person.changeMbti(mbti);
        em.persist(person);
        return person;
    }
}
