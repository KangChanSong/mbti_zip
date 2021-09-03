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
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class File {
    @EmbeddedId
    private FileId fileId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "job_id")
    private Job job;


    //== 연관관계 메서드 ==//

    public void setPerson(Person person) {
        this.person = person;
        person.setFile(this);
    }

    public void setJob(Job job) {
        this.job = job;
        job.setFile(this);
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
