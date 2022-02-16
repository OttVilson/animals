package com.vilson.day;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;

import java.util.ArrayList;
import java.util.List;

class FakeAnimalsProvider implements AnimalsProvider {
    private List<Animal> animals;

    @Override
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    void provideAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
