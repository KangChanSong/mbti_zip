package com.mbtizip.repository.test;

import javax.persistence.EntityManager;

public class TestRepository {

    private final EntityManager em;
    private TestJobRepository jobRepository;
    private TestPersonRepository personRepository;
    private TestMbtiRepository mbtiRepository;
    private TestFileRepository fileRepostiroy;
    private TestCategoryRepository categoryRepository;

    public TestRepository(EntityManager em) {
        this.em = em;
        this.jobRepository = new TestJobRepository(em);
        this.personRepository = new TestPersonRepository(em);
        this.mbtiRepository = new TestMbtiRepository(em);
        this.fileRepostiroy = new TestFileRepository(em);
        this.categoryRepository = new TestCategoryRepository(em);
    }

    public TestJobRepository getJobRepository() {
        return jobRepository;
    }

    public TestPersonRepository getPersonRepository() {
        return personRepository;
    }

    public TestMbtiRepository getMbtiRepository() {
        return mbtiRepository;
    }

    public TestFileRepository getFileRepostiroy() {
        return fileRepostiroy;
    }

    public TestCategoryRepository getCategoryRepository() {
        return categoryRepository;
    }
}
