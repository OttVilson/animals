package com.vilson.animals;

class Cat extends Animal {

    Cat(String name, String favoriteFood) {
        super(name, favoriteFood);
    }

    @Override
    public String toString() {
        String format = "The cat %s likes to eat %s";
        return String.format(format, name, favoriteFood);
    }
}
