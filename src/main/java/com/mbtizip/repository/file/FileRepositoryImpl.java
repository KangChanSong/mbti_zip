package com.mbtizip.repository.file;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.file.QFile;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

import static com.mbtizip.util.ErrorMessageProvider.NO_ENTITY_FOUND;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public FileId save(File file) {
        em.persist(file);
        return file.getFileId();
    }

    @Override
    public Long countByFileId(FileId fileId) {
        QFile file = QFile.file;

        return queryFactory.selectFrom(file)
                .where(file.fileId.eq(fileId))
                .fetchCount();
    }

    @Override
    public File find(FileId fileId) {
        try {
            return (File) em.createQuery("select f from File f" +
                            " where f.fileId =: fileId")
                    .setParameter("fileId", fileId)
                    .getSingleResult();
        } catch (RuntimeException e){
            throw new NoEntityFoundException(NO_ENTITY_FOUND + " fileId : " + fileId.toString());
        }
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
    public List<File> findAllNotRegistered() {
        return em.createQuery("select f from File f " +
                "where f.person is null and f.job is null")
                .getResultList();
    }


    @Override
    public void delete(File file) {
        log.info("파일 DB에서 삭제");
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
        log.info("파일 DB에서 삭제");
        String name = cls.getSimpleName().toLowerCase(Locale.ROOT);
        em.createQuery("delete from File f" +
                        " where f." + name + ".id =: id")
                .setParameter("id", id)
                    .executeUpdate();
    }


}
