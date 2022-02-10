package com.vilson.animals;

class Bird extends Animal {

    final double wingspanInMeters;

    Bird(String name, String favoriteFood, double wingspanInMeters) {
        super(name, favoriteFood);
        this.wingspanInMeters = wingspanInMeters;
    }
}
