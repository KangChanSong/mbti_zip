package com.mbtizip.repository.test;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.person.PersonRepository;

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

    public Person createPersonWithMbti(Mbti mbti){
        Person person = Person.builder()
                .mbti(mbti)
                .name(PERSON_NAME).build();
        em.persist(person);
        return person;
    }
}
