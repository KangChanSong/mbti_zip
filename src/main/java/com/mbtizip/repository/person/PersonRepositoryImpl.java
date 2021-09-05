package com.mbtizip.repository.person;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.domain.personCategory.QPersonCategory;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.common.CommonRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public Long save(Person person) {
        em.persist(person);
        return person.getId();
    }

    @Override
    public Person find(Long id) {
        QPerson person = QPerson.person;
        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti, QMbti.mbti)
                .leftJoin(person.personCategories, QPersonCategory.personCategory)
                .leftJoin(person.file, QFile.file)
                .where(person.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Person> findAll() {
        return queryFactory.selectFrom(QPerson.person).fetch();
    }
    @Override
    public List<Person> findAll(Page page) {
        List<Long> extractedIds = queryFactory.select(QPerson.person.id).from(QPerson.person)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();

        return joinFetch(extractedIds);
    }

    private List<Person> joinFetch(List<Long> extractedIds){
        QPerson person = QPerson.person;
        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti , QMbti.mbti)
                .leftJoin(person.personCategories, QPersonCategory.personCategory)
                .where(person.id.in(extractedIds))
                .fetch();
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort) {
        List<Long> extractedIds = queryFactory.select(QPerson.person.id).from(QPerson.person)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();

        return joinFetch(extractedIds);
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        List<Long> extractedIds = queryFactory.select(QPerson.person.id).from(QPerson.person)
                .where(keyword)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();

        return joinFetch(extractedIds);
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


}
