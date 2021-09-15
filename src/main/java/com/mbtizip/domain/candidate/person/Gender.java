package com.mbtizip.domain.candidate.person;

public enum Gender {

    MALE("male"), FEMALE("female");

    private String text;

    Gender(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
