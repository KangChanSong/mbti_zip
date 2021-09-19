package com.mbtizip.repository;

import com.mbtizip.common.enums.TestCategoryEnum;
import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.candidate.person.QPerson;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.repository.candidate.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mbtizip.common.enums.TestCategoryEnum.CATEGORY_NAME_1;
import static com.mbtizip.common.util.TestEntityGenerator.createCategory;
import static com.mbtizip.common.util.TestEntityGenerator.createPerson;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CandidateRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    CandidateRepository repository;

    Category category;

    @BeforeEach
    public void setup(){
        Category createdCategory = createCategory(CATEGORY_NAME_1);
        em.persist(createdCategory);
        category = em.find(Category.class, createdCategory.getId());
    }

    @DisplayName("Person 이 등록된다")
    @Test
    public void Candidate_등록(){

        //given
        Person person = createPerson();
        //when
        person.setCategory(category);

        Long id = repository.save(person);
        Person found = (Person) repository.find(id);
        //then
        assertEquals(id, found.getId());
    }


    // 목록 조회
    @DisplayName("Person 에 대한 목록이 조회된다.")
    @Test
    public void Candidate_목록_조회_Person(){
        //given
        String name = "name";
        savePersonsWithName(name);

        //then
        List<Person> found = repository.findAll(Person.class,
                                                    Page.builder()
                                                        .pageNum(1)
                                                        .amount(10).build());
        assertEquals(3, found.size());

        found.forEach(person -> {
            assertTrue(person.getName().contains(name));
        });

    }

    @DisplayName("Job 에 대한 목록이 조회된다.")
    @Test
    public void Candidate_목록_조회_Job(){

        //given
        String title = "title";
        String title1 = title + 1;
        String title2 = title + 2;
        String title3 = title + 3;

        Job job1 = TestEntityGenerator.createJob(title1);
        Job job2 = TestEntityGenerator.createJob(title2);
        Job job3 = TestEntityGenerator.createJob(title3);

        //when
        repository.save(job1);
        repository.save(job2);
        repository.save(job3);

        Page page = Page.builder()
                .pageNum(1)
                .amount(10)
                .build();

        //then
        List<Job> found = repository.findAll(Job.class, page);
        assertEquals(3, found.size());
        found.forEach(job -> {
            assertTrue(job.getTitle().contains(title));
        });

    }

    @DisplayName("잘못된 Class 파라미터에 대해 예외를 던진다.")
    @Test
    public void 잘못된_Class(){

        //then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            repository.findAll(Candidate.class, Page.builder().pageNum(1).amount(10).build());
        });
    }

    @DisplayName("총 로우 갯수를 집계한다")
    @Test
    public void 집계(){
        //when
        savePersonsWithName("name");
        //then
        Long count = repository.countAll(Person.class);
        assertEquals(3, count);
    }

    @DisplayName("where 조건에 맞게 집계한다")
    @Test
    public void 집계_조건(){

        //when
        savePersonsWithName("name");
        //then
        Long count = repository.countAll(Person.class,
                QPerson.person.name.like("%1%"));

        assertEquals(1, count);
    }

    private void savePersonsWithName(String name){
        //given
        Person person1 = createPerson(name + "1");
        Person person2 = createPerson(name + "2");
        Person person3 = createPerson(name +"3");
        //when
        person1.setCategory(category);
        person2.setCategory(category);
        person3.setCategory(category);

        repository.save(person1);
        repository.save(person2);
        repository.save(person3);
    }
}
