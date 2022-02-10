package com.vilson.model;

public class Dog extends Animal {
    private final String breed;

    private static final String TO_STRING_FORMAT = "The dog %s of breed %s likes to eat %s.";

    public Dog(String name, String favoriteFood, String breed) {
        super(name, favoriteFood);
        this.breed = breed;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, name, breed, favoriteFood);
    }
}
