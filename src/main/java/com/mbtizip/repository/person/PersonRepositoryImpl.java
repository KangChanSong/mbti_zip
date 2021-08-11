package com.mbtizip.repository.person;

import com.mbtizip.domain.comment.Page;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.common.CommonRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.TypeCache;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository{

    private final EntityManager em;

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
    public List<Person> findAllWithPaging(Page page) {
        return em.createQuery("select p from Person p" +
                " left join fetch p.mbti m")
                .setFirstResult(page.getStart())
                .setMaxResults(page.getEnd())
                .getResultList();
    }

    @Override
    public void modifyLikes(Person person, Boolean isIncrease) {
        CommonRepository.modifyLikes(em, Person.class, person.getId(), isIncrease);
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
