package com.mbtizip.repository.file;

import com.mbtizip.domain.candidate.Candidate;
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

    private final QFile file = QFile.file;

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
    public File findByCandidate(Candidate candidate) {
        return queryFactory.selectFrom(file)
                .where(file.candidate.eq(candidate))
                .fetchOne();
    }

    @Override
    public List<File> findAllNotRegistered() {
        return queryFactory.selectFrom(file)
                .where(file.candidate.isNull())
                .fetch();
    }

    @Override
    public void delete(File file) {
        log.info("파일 DB에서 삭제");
        em.remove(file);
    }

    @Override
    public void deleteByCandidate(Candidate candidate) {
        queryFactory.delete(file).where(file.candidate.eq(candidate));
    }

}
