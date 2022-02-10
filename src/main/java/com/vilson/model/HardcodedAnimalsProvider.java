package com.vilson.model;

import java.util.ArrayList;
import java.util.List;

import static com.vilson.model.LiteralConstants.*;

public class HardcodedAnimalsProvider implements AnimalsProvider {

    @Override
    public List<Animal> getAnimals() {
        List<Animal> animals = new ArrayList<>();
        addDogs(animals);
        addCats(animals);
        return animals;
    }

    private void addDogs(List<Animal> animals) {
        Animal rex = new Dog("Rex", ROYAL_CANIN, SHEPERD);
        Animal max = new Dog("Max", PURINA_ONE, SHEPERD);
        Animal tom = new Dog("Tom", ROYAL_CANIN, HUSKY);
        Animal jay = new Dog("Jay", PURINA_ONE, HUSKY);

        animals.add(rex);
        animals.add(max);
        animals.add(tom);
        animals.add(jay);
    }

    private void addCats(List<Animal> animals) {
        Animal zoe = new Cat("Zoe", NINE_LIVES);
        Animal ada = new Cat("Ada", PURINA_FRISKIES);

        animals.add(zoe);
        animals.add(ada);
    }

}
