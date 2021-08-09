package com.mbtizip.repository.test;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.person.PersonRepository;

public class PersonTestHelper {
    public static final String PERSON_NAME ="송강찬";

    PersonRepository personRepository;

    public PersonTestHelper(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(){
        Person person = Person.builder().name(PERSON_NAME).build();
        personRepository.save(person);
        return person;
    }

    public Person createPersonWithMbti(Mbti mbti){
        Person person = Person.builder()
                .mbti(mbti)
                .name(PERSON_NAME).build();
        personRepository.save(person);
        return person;
    }
}
