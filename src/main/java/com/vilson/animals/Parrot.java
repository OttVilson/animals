package com.vilson.animals;

import static com.vilson.animals.Flagged.doesOrDoesnt;

class Parrot extends Bird {

    private final boolean canSpeak;

    Parrot(String name, String favoriteFood, double wingspanInMeters, boolean canSpeak) {
        super(name, favoriteFood, wingspanInMeters);
        this.canSpeak = canSpeak;
    }

    @Override
    public String toString() {
        String format = "The parrot %s likes to eat %s, has wingspan %fm, and %s speak.";
        return String.format(format, name, favoriteFood, wingspanInMeters, doesOrDoesnt(canSpeak));
    }
}
