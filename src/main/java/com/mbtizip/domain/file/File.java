package com.mbtizip.domain.file;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import static java.util.UUID.randomUUID;

@NoArgsConstructor
@Getter @Setter
@Entity
public class File {
    @EmbeddedId
    private FileId fileId;
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

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
