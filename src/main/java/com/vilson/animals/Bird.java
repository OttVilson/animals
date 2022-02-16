package com.vilson.animals;

class Bird extends Animal {
    final static String WINGSPAN_FORMAT = "%.2fm";

    final double wingspanInMeters;

    Bird(String name, String favoriteFood, double wingspanInMeters) {
        super(name, favoriteFood);
        this.wingspanInMeters = wingspanInMeters;
    }
}
