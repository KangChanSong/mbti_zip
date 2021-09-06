package com.mbtizip.repository;

import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.domain.personCategory.QPersonCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JoinTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @DisplayName("단방향관계 조인 테스트")
    @Test
    public void JOIN(){
        //given
        QPerson person = QPerson.person;
        //when
        List<Person> persons = queryFactory.selectFrom(person)
                .leftJoin(person.personCategories, QPersonCategory.personCategory)
                .fetchJoin()
                .fetch();
        //then
        persons.forEach(p -> assertNotNull(p.getPersonCategories()));
    }
}
