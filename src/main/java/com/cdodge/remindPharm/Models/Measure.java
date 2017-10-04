package com.cdodge.remindPharm.Models;

public enum Measure {
    MILLILITER ("ml"),
    MILLIGRAM ("mg"),
    GRAM("g"),
    PILL("pill")
    ;

    private String shorthand;

    Measure(String shorthand) {
        this.shorthand = shorthand;
    }

    public String getShorthand() {
        return shorthand;
    }
}
