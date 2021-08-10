package com.mbtizip.common.enums;

public enum TestJobEnum {
    JOB_TITLE("개발자"), JOB_WRITER("송강찬");

    private String text;
    TestJobEnum(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
