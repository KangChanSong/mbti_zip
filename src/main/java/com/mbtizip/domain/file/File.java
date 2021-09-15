package com.mbtizip.domain.file;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import static java.util.UUID.randomUUID;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class File {
    @EmbeddedId
    private FileId fileId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;


    //== 연관관계 메서드 ==//
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    //== 편의 메서드 ==//
    public String getFileName(){
        return this.fileId.getUuid() + "_" + this.fileId.getName();
    }

    // == 편의 생성자 메서드 ==//
    public File(FileId fileId){
        this.fileId = fileId;
    }

    public File(MultipartFile multipartFile) {
        this.fileId = FileId.builder()
                .uuid(randomUUID().toString())
                .name(multipartFile.getOriginalFilename()).build();
    }
}
