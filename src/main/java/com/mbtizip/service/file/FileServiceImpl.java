package com.mbtizip.service.file;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.service.file.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.mbtizip.util.ErrorMessageProvider.NO_ENTITY_FOUND;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;
    private final CandidateRepository candidateRepository;
    private final StoreService storeService;

    @Override
    public File getByCandidate(Candidate candidate) {
        return fileRepository.findByCandidate(candidate);
    }

    @Transactional
    @Override
    public String upload(MultipartFile multipartFile) {
        FileId saved = fileRepository.save(new File(multipartFile));
        storeService.storeInLocal(multipartFile, saved.getUuid());
        return saved.getUuid() + "_" + saved.getName();
    }

    @Transactional
    @Override
    public void saveFileWithCandidate(Long id, String fullname) {
        Candidate candidate = candidateRepository.find(id);
        if(candidate == null) throw new IllegalArgumentException(NO_ENTITY_FOUND + " id = " + id);
        File file = fileRepository.find(new FileId(fullname));
        file.setCandidate(candidate);
    }

    @Transactional
    @Override
    public void delete(String filename) {
        File file = fileRepository.find(new FileId(filename));
        fileRepository.delete(file);
        storeService.deleteFromLocal(file);
    }

    @Transactional
    @Override
    public Boolean deleteFileWithCandidate(Candidate candidate) {
        fileRepository.deleteByCandidate(candidate);
        return true;
    }
}
