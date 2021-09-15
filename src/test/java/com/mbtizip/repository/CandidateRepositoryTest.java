package com.mbtizip.repository;

import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
public class CandidateRepositoryTest {

    @Autowired
    CandidateRepository repository;

    // 등록
    @Test
    public void Candidate_등록(){

        //given
        Candidate candidate = TestEntityGenerator.createPerson();
        //when
        Long id = repository.save(candidate);
        //then
        assertEquals(id, candidate.getId());
    }

    // 조회

    // 목록 조회
}
