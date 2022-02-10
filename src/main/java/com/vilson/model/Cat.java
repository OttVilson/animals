package com.vilson.model;

public class Cat extends Animal {

    private static final String TO_STRING_FORMAT = "The cat %s likes to eat %s";

    public Cat(String name, String favoriteFood) {
        super(name, favoriteFood);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, name, favoriteFood);
    }
}
