package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;

import java.util.ArrayList;
import java.util.List;

public class FakeAnimalsProvider implements AnimalsProvider {
    private List<Animal> animals;

    @Override
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    public void provideAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public void appendAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
}
