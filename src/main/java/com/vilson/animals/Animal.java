package com.vilson.animals;

public class Animal {

    final String name;
    final String favoriteFood;

    Animal(String name, String favoriteFood) {
        this.name = name;
        this.favoriteFood = favoriteFood;
    }

    public String getName() {
        return name;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }
}
