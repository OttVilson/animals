package com.vilson.animals;

import static com.vilson.animals.Flagged.doesOrDoesnt;

class Chicken extends Bird {

    private final boolean laysEggs;

    Chicken(String name, String favoriteFood, double wingspanInMeters, boolean laysEggs) {
        super(name, favoriteFood, wingspanInMeters);
        this.laysEggs = laysEggs;
    }

    @Override
    public String toString() {
        String format = "The chicken %s loves to eat %s, has wingspan %fm, and %s lay eggs.";
        return String.format(format, name, favoriteFood, wingspanInMeters, doesOrDoesnt(laysEggs));
    }
}
