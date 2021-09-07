package com.mbtizip.repository.person;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.domain.personCategory.QPersonCategory;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.common.CommonRepository;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.ListExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    private final QPerson person = QPerson.person;

    @Override
    public Long save(Person person) {
        em.persist(person);
        return person.getId();
    }

    @Override
    public Person find(Long id) {
        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti, QMbti.mbti)
                .fetchJoin()
                .leftJoin(person.personCategories, QPersonCategory.personCategory)
                .fetchJoin()
                .leftJoin(person.file, QFile.file)
                .fetchJoin()
                .where(person.id.eq(id))
                .fetchOne();
    }


    @Override
    public List<Person> findAll() {
        return queryFactory.selectFrom(QPerson.person).fetch();
    }
    @Override
    public List<Person> findAll(Page page) {
        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti, QMbti.mbti)
                .fetchJoin()
                .leftJoin(person.file, QFile.file)
                .fetchJoin()
                .offset(page.getOffset())
                .limit(page.getAmount()).fetch();
    }


    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort) {
        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti, QMbti.mbti)
                .fetchJoin()
                .leftJoin(person.file, QFile.file)
                .fetchJoin()
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount()).fetch();
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return queryFactory.selectFrom(person)
                .where(keyword)
                .leftJoin(person.mbti, QMbti.mbti)
                .fetchJoin()
                .leftJoin(person.file, QFile.file)
                .fetchJoin()
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount()).fetch();
    }

    @Override
    public void modifyLikes(Person person, Boolean isIncrease) {
        CommonRepository.modifyLikes(em, Person.class, person.getId(), isIncrease);
    }

    @Override
    public void remove(Person person) {
        em.remove(person);
    }

    @Override
    public Long countAll() {
        return (Long)em.createQuery("select count(p) from Person p")
                .getSingleResult();
    }

    @Override
    public Long countByName(String name) {
        return queryFactory.selectFrom(person)
                .where(person.name.eq(name))
                .fetchCount();
    }

}
