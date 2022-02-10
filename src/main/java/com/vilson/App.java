package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.animals.HardcodedAnimalsProvider;
import com.vilson.day.Lunch;
import com.vilson.output.ConsoleOutput;

public class App {

    public static void main(String[] args) {
        AnimalsProvider animalsProvider = new HardcodedAnimalsProvider();
        for (Animal animal : animalsProvider.getAnimals()) {
            System.out.println(animal);
            System.out.println(animal.getClass());
        }

        (new Lunch(animalsProvider, new ConsoleOutput())).outputEatersGroupedByFood();
    }

}
