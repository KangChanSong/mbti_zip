package com.mbtizip.domain.file;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Embeddable
public class FileId implements Serializable {

    private String name;
    private String uuid;

    // == 편의 생성자 메서드 ==//
    public FileId(String fullName){

        if(!fullName.contains("_")) throw new IllegalArgumentException("파일 이름에 언더스코어(\"_\")가 없습니다. 파일이름: " + fullName);

        String[] splited = fullName.split("_");
        String uuid = splited[0];
        String name = splited[1];
        if(splited.length > 2){
            for(int i = 2 ; i < splited.length ; i++){
                name += ("_" + splited[i]);
            }
        }

        this.uuid = uuid;
        this.name = name;
    }
}
