package com.mbtizip.repository;

import com.mbtizip.common.enums.TestFileEnum;
import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.mbtizip.common.enums.TestFileEnum.FILE_NAME;
import static com.mbtizip.common.enums.TestFileEnum.FILE_UUID;
import static com.mbtizip.common.util.TestEntityGenerator.createFile;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    EntityManager em;

    TestPersonRepository testPersonRepository;
    TestJobRepository testJobRepository;

    @BeforeEach
    public void setup(){

        this.testPersonRepository = new TestPersonRepository(em);
        this.testJobRepository = new TestJobRepository(em);
    }
    
    //파일 person으로 조회
    @Test
    public void 파일_PERSON_조회(){

        //given
        File file = createFile();
        Person person = testPersonRepository.createPerson();
        file.setPerson(person);

        //when
        fileRepository.save(file);

        //then
        File findFile = fileRepository.findByPerson(person);

        assertFileWithPerson(findFile, person);
    }

    @Test
    public void 파일_JOB_조회(){
        //given
        File file = createFile();
        Job job = testJobRepository.createJob();
        file.setJob(job);

        //when
        fileRepository.save(file);

        //then
        File findFile =fileRepository.findByJob(job);

        assertFileWithJob(findFile, job);

    }

    //person 을 참조하는 file 삭제
    @Test
    public void 파일_PERSON_삭제(){

        //given
        File file = createFile();
        Person person = testPersonRepository.createPerson();
        file.setPerson(person);

        //when
        fileRepository.save(file);
        fileRepository.deleteByPerson(person);

        //then
        assertThrows(NoEntityFoundException.class, () -> fileRepository.findByPerson(person));
    }

    @Test
    public void 파일_JOB_삭제(){

        //given
        File file = createFile();
        Job job = testJobRepository.createJob();
        file.setJob(job);

        //when
        fileRepository.save(file);
        fileRepository.deleteByJob(job);

        //then

        assertThrows(NoEntityFoundException.class, () -> fileRepository.findByJob(job));
    }

    private void assertFileWithPerson(File findFile, Person person){
        assertSame(person, findFile.getPerson());
        assertFile(findFile);
    }

    private void assertFileWithJob(File findFile , Job job){
        assertSame(job, findFile.getJob());
        assertFile(findFile);
    }

    private void assertFile(File findFile){
        assertEquals(FILE_NAME.getText(), findFile.getName());
        assertEquals(FILE_UUID.getText(), findFile.getUuid());
    }
}
