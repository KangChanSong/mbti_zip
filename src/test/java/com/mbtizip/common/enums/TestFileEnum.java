package com.mbtizip.common.enums;

public enum TestFileEnum {
    FILE_UUID("file_uuid"),
    FILE_NAME("file_name");

    private String text;
    TestFileEnum(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
