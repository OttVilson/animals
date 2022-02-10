package com.vilson.animals;

class Dog extends Animal {

    private final String breed;

    Dog(String name, String favoriteFood, String breed) {
        super(name, favoriteFood);
        this.breed = breed;
    }

    @Override
    public String toString() {
        String format = "The dog %s of breed %s likes to eat %s.";
        return String.format(format, name, breed, favoriteFood);
    }
}
