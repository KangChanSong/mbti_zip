package com.mbtizip.service.file.storage;

import org.springframework.stereotype.Component;

@Component
public class StorageProperties {
    private String location = "C:/temp_files";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
