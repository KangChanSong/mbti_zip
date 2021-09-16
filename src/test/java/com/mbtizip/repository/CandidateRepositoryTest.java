package com.mbtizip.repository;

import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.candidate.person.QPerson;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.repository.candidate.CandidateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mbtizip.common.util.TestEntityGenerator.createPerson;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CandidateRepositoryTest {

    @Autowired
    CandidateRepository repository;

    @DisplayName("Person 이 등록된다")
    @Test
    public void Candidate_등록(){

        //given
        Candidate candidate = createPerson();
        //when
        Long id = repository.save(candidate);
        //then
        assertEquals(id, candidate.getId());
    }

    @DisplayName("Person 이 조회된다")
    @Test
    public void Candidate_조회(){

        //given
        Candidate candidate = createPerson();
        //when
        Long id = repository.save(candidate);

        //then
        Candidate found = repository.find(id);
        assertEquals(candidate, found);
        assertEquals(candidate.getId(), found.getId());
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
        repository.save(person1);
        repository.save(person2);
        repository.save(person3);
    }
}
