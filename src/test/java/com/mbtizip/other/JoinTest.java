package com.mbtizip.other;

import com.mbtizip.domain.candidate.person.Gender;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class JoinTest {

    private static final int MAX = 10000;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setup(){
        insertMbtis();
        IntStream.range(0, MAX).forEach(i -> {
            insertPersons(i);
            insertFiles(i);
            insertCategories(i);
        });

        setAssociation();
    }

    @Test
    public void SETUP_잘됐다(){
        List list = em.createQuery("select p from Person p").getResultList();
        assertEquals(MAX, list.size());
    }

    @DisplayName("left outer join 과 inner join 성능 비교")
    @Test
    public void LEFT_VS_INNER(){
        //given
        double outer = measure(() -> executeOuterJoin());
        double inner = measure(() -> executeInnerJoin());
        //then
        System.out.println(inner - outer);
        assertTrue(inner < outer);
    }

    private double measure(Runnable method){

        double repeat = 1000.0;
        double sum = 0;
        for(int i = 0 ; i < repeat ; i++){
            Date start = new Date();
            method.run();
            Date end = new Date();
            sum += (end.getTime() - start.getTime());
        }

        return sum / repeat;
    }

    private void executeOuterJoin(){
        em.createQuery("select p from Person p " +
                " left outer join fetch p.mbti " +
                " left outer join fetch p.category " +
                " left outer join fetch p.file")
                .getResultList();
    }

    private void executeInnerJoin(){
        em.createQuery("select p from Person p" +
                " inner join fetch p.mbti" +
                " inner join fetch p.category" +
                " inner join fetch p.file")
                .getResultList();
    }


    private void setAssociation() {
        List<Person> persons = em.createQuery("select p from Person p").getResultList();
        List<Category> categories = em.createQuery("select c from Category c").getResultList();
        List<File> files = em.createQuery("select f from File f").getResultList();
        Mbti mbti = (Mbti) em.createQuery("select m from Mbti m").getSingleResult();

        IntStream.range(0, MAX).forEach(i -> {
            Person person = persons.get(i);
            Category category = categories.get(i);
            File file = files.get(i);
            person.setCategory(category);
            file.setCandidate(person);
        });
    }

    private void insertPersons(int i) {
        em.persist(Person.builder()
                    .name("name" + i)
                    .writer("writer" + i)
                    .description("description" + i)
                    .gender(Gender.MALE)
                    .password("1234")
                    .build());
    }

    private void insertMbtis() {
        em.persist(Mbti.builder().name(MbtiEnum.INFP).build());
    }

    private void insertFiles(int i){
            em.persist(new File(new FileId("asdsasd_file" + i)));
    }

    private void insertCategories(int i){
        em.persist(Category.builder().name("category" + i).build());
    }
}
