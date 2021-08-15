package com.mbtizip.repository.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.person.Person;

public interface FileRepository {
    Long save(File file);
    File find(Long saveId);
    File findByPerson(Person person);
    void delete(File file);
    void deleteByPerson(Person findPerson);
}
