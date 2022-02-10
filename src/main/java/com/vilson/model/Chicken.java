package com.vilson.model;

public class Chicken extends Bird {
    private final boolean laysEggs;

    private static final String TO_STRING_FORMAT = "The chicken %s loves to eat %s, has wingspan %fm, " +
            "and %s lay eggs.";

    public Chicken(String name, String favoriteFood, double wingspanInMeters, boolean laysEggs) {
        super(name, favoriteFood, wingspanInMeters);
        this.laysEggs = laysEggs;
    }

    @Override
    public String toString() {
        String verb = "does" + (laysEggs ? "" : "n't");
        return String.format(TO_STRING_FORMAT, name, favoriteFood, wingspanInMeters, verb);
    }
}
