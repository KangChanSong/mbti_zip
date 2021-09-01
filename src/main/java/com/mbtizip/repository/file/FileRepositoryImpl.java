package com.mbtizip.repository.file;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

@Slf4j
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
         return findByObject(Person.class, person.getId());
    }
    @Override
    public File findByJob(Job job) {
        return findByObject(Job.class, job.getId());
    }
    @Override
    public void delete(File file) {
        em.remove(file);
    }

    @Override
    public void deleteByPerson(Person person) {
        deleteByObject(Person.class, person.getId());
    }
    @Override
    public void deleteByJob(Job job) {
        deleteByObject(Job.class, job.getId());
    }

    private <T extends CommonEntity> File findByObject(Class<T> cls, Long id){
        String name = cls.getSimpleName();

        try {
            return (File) em.createQuery("select f from File f" +
                        " where f." + name.toLowerCase(Locale.ROOT) + ".id =: id")
                        .setParameter("id", id)
                        .getSingleResult();

        } catch(RuntimeException e){
            throw new NoEntityFoundException("파일를 찾을 수 없습니다. Class : " + cls.getName() + " , id : " + id);
        }
    }

    private <T extends CommonEntity> void deleteByObject(Class<T> cls, Long id){
        String name = cls.getSimpleName().toLowerCase(Locale.ROOT);
        em.createQuery("delete from File f" +
                        " where f." + name + ".id =: id")
                .setParameter("id", id)
                    .executeUpdate();
    }
}
