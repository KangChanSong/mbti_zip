package com.mbtizip.repository.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository{

    private final EntityManager em;

    @Override
    public Long save(File file) {
        em.persist(file);
        return file.getId();
    }

    @Override
    public File find(Long saveId) {
        return em.find(File.class, saveId);
    }

    @Override
    public File findByPerson(Person person) {
        return (File) em.createQuery("select f from File f"
                    +" join fetch f.person p"
                    +" where f.person.id =: id")
                .setParameter("id", person.getId())
                .getResultList().get(0);
    }
}
