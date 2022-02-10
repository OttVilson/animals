package com.vilson;

import com.vilson.model.Animal;
import com.vilson.model.AnimalsProvider;
import com.vilson.model.HardcodedAnimalsProvider;

public class App {

    public static void main(String[] args) {
        AnimalsProvider animalsProvider = new HardcodedAnimalsProvider();
        for (Animal animal : animalsProvider.getAnimals())
            System.out.println(animal);
    }

}
