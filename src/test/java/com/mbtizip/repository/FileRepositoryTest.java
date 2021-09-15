package com.mbtizip.repository;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.repository.test.TestRepository;
import org.junit.jupiter.api.BeforeEach;
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
    private TestRepository testRepository;
    FileId fileId;

    @BeforeEach
    public void setup(){
        this.testRepository = new TestRepository(em);
        fileId = fileId.builder().uuid(FILE_UUID.getText())
                                    .name(FILE_NAME.getText())
                .build();
    }

    //@Test
    public void 파일_등록(){

        //when
        File file = new File(fileId);
        fileRepository.save(file);

        //then
        File found = fileRepository.find(fileId);
        assertEquals(found.getFileId(), fileId);
    }

    //파일 person으로 조회
   // @Test
    public void 파일_PERSON_조회(){

        //given
        File file = createFile();
        Person person = testRepository.getPersonRepository().createPerson();
        file.setCandidate(person);

        //when
        fileRepository.save(file);

        //then
        File findFile = fileRepository.findByCandidate(person);

        assertFileWithPerson(findFile, person);
    }

   // @Test
    public void 파일_JOB_조회(){
        //given
        File file = createFile();
        Job job = testRepository.getJobRepository().createJob();
        file.setCandidate(job);

        //when
        fileRepository.save(file);

        //then
        File findFile =fileRepository.findByCandidate(job);

        assertFileWithJob(findFile, job);

    }

    //person 을 참조하는 file 삭제
   // @Test
    public void 파일_PERSON_삭제(){

        //given
        File file = createFile();
        Person person = testRepository.getPersonRepository().createPerson();
        file.setCandidate(person);

        //when
        fileRepository.save(file);
        fileRepository.deleteByCandidate(person);

        //then
        assertThrows(NoEntityFoundException.class, () -> fileRepository.findByCandidate(person));
    }

    //@Test
    public void 파일_JOB_삭제(){

        //given
        File file = createFile();
        Job job = testRepository.getJobRepository().createJob();
        file.setCandidate(job);

        //when
        fileRepository.save(file);
        fileRepository.deleteByCandidate(job);

        //then

        assertThrows(NoEntityFoundException.class, () -> fileRepository.findByCandidate(job));
    }

    private void assertFileWithPerson(File findFile, Person person){
        assertSame(person, findFile.getCandidate());
        assertFile(findFile);
    }

    private void assertFileWithJob(File findFile , Job job){
        assertSame(job, findFile.getCandidate());
        assertFile(findFile);
    }

    private void assertFile(File findFile){
        assertEquals(FILE_NAME.getText(), findFile.getFileId().getName());
        assertEquals(FILE_UUID.getText(), findFile.getFileId().getUuid());
    }
}
