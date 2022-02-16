package com.vilson.animals;

class Rooster extends Bird {

    Rooster(String name, String favoriteFood, double wingspanInMeters) {
        super(name, favoriteFood, wingspanInMeters);
    }

    @Override
    public String toString() {
        String format = "The rooster %s likes to eat %s, and has wingspan " + WINGSPAN_FORMAT + ".";
        return String.format(format, name, favoriteFood, wingspanInMeters);
    }
}
