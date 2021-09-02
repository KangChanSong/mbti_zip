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
}
