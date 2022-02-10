package com.vilson.model;

public class Rooster extends Bird {

    private static final String TO_STRING_FORMAT = "The rooster %s likes to eat %s, and has wingspan %fm.";

    Rooster(String name, String favoriteFood, double wingspanInMeters) {
        super(name, favoriteFood, wingspanInMeters);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, name, favoriteFood);
    }
}
