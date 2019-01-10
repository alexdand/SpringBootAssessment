package com.solstice.spring_assessment.model;

public enum AccountTypeEnum {

    ARS("ARS"),
    USD("USD");

    private final String text;

    AccountTypeEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
