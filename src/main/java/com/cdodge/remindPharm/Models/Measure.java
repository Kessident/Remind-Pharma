package com.cdodge.remindPharm.Models;

public enum Measure {
    MILLILITER ("mL"),
    MILLIGRAM ("mg"),
    GRAM("g"),
    ;

    private String shorthand;

    Measure(String shorthand) {
        this.shorthand = shorthand;
    }

    public String getShorthand() {
        return shorthand;
    }
}
