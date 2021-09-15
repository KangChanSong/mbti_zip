package com.mbtizip.repository.person;

import com.mbtizip.domain.category.QCategory;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.candidate.person.QPerson;
import com.mbtizip.repository.common.CommonRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
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
        return ((JPAQuery<Person>)getJoinQuery()
                .where(person.id.eq(id)))
                .fetchOne();
    }


    @Override
    public List<Person> findAll() {
        return queryFactory.selectFrom(QPerson.person).fetch();
    }
    @Override
    public List<Person> findAll(Page page) {
        return ((JPAQuery) getJoinQuery()
                .offset(page.getOffset())
                .limit(page.getAmount())).fetch();
    }


    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort) {
        return ((JPAQuery) getJoinQuery()
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())).fetch();
    }

    @Override
    public List<Person> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword) {
        log.info("키워드와 함께 인물 목록 조회 ");
        return ((JPAQuery) getJoinQuery()
                .where(keyword)
                .orderBy(sort)
                .offset(page.getOffset())
                .limit(page.getAmount())).fetch();
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

    private JPAQueryBase getJoinQuery(){
        final QCategory category = QCategory.category;
        final QFile file = QFile.file;
        final QMbti mbti = QMbti.mbti;

        return queryFactory.selectFrom(person)
                .leftJoin(person.mbti, mbti)
                .fetchJoin()
                .leftJoin(person.category, category)
                .fetchJoin()
                .leftJoin(person.file, file)
                .fetchJoin();
    }

}
