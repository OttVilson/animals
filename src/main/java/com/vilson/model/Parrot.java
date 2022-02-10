package com.vilson.model;

public class Parrot extends Bird {
    private final boolean canSpeak;

    private static final String TO_STRING_FORMAT =
            "The parrot %s likes to eat %s, has wingspan %fm, and %s speak.";

    public Parrot(String name, String favoriteFood, double wingspanInMeters, boolean canSpeak) {
        super(name, favoriteFood, wingspanInMeters);
        this.canSpeak = canSpeak;
    }

    @Override
    public String toString() {
        String verb = canSpeak ? "does" : "doesn't";
        return String.format(TO_STRING_FORMAT, name, favoriteFood, wingspanInMeters, verb);
    }
}
