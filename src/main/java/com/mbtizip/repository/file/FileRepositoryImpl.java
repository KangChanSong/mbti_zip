package com.mbtizip.repository.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
         List<File> fileList = em.createQuery("select f from File f"
                        + " join fetch f.person p"
                        + " where f.person.id =: id")
                .setParameter("id", person.getId())
                .getResultList();

         if(fileList.isEmpty()) throw new IllegalArgumentException("person 에 해당하는 file을 찾을 수 없습니다. personId : " + person.getId());
         else return fileList.get(0);
    }

    @Override
    public void delete(File file) {
        em.remove(file);
    }

    @Override
    public void deleteByPerson(Person findPerson) {
        em.createQuery("delete from File f" +
                " where f.person.id =: id")
                .setParameter("id", findPerson.getId())
                .executeUpdate();
    }
}
