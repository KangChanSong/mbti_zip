package com.mbtizip.repository.person;

import com.mbtizip.domain.common.pageSortFilter.Page;
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

    private QPerson qPerson = QPerson.person;
    private QMbti qMbti = QMbti.mbti;
    private QPersonCategory qPersonCategory = QPersonCategory.personCategory;

    @Override
    public Long save(Person person) {
        em.persist(person);
        return person.getId();
    }

    @Override
    public Person find(Long id) {
        Person person = em.find(Person.class, id);
        if(person == null) throw new NoEntityFoundException("Person 을 찾을 수 없습니다. id = " + id);
        return person;
    }

    @Override
    public List<Person> findAll() {
        return em.createQuery("select p from Person p " +
                        "join fetch p.mbti")
                .getResultList();
    }

    @Override
    public List<Person> findAll(Page page) {
        return joinQuery()
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort) {
        return joinQuery()
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())
                .fetch();
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        return joinQuery()
                .where(keyword)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount()).fetch();

    }

    private JPAQuery<Person> joinQuery(){
        return queryFactory.selectFrom(qPerson)
                .leftJoin(qPerson.mbti, qMbti);
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
    public Person findWithMbti(Long saveId) {
        return (Person) em.createQuery("select p from Person p" +
                " left join fetch p.mbti m" +
                " where p.id =: id")
                .setParameter("id", saveId)
                .getResultList().get(0);
    }

}
