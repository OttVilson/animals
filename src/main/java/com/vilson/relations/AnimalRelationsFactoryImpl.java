package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;

import java.util.List;
import java.util.stream.Collectors;

class AnimalRelationsFactoryImpl implements AnimalRelationsFactory {

    private final AnimalsProvider animalsProvider;

    AnimalRelationsFactoryImpl(AnimalsProvider animalsProvider) {
        this.animalsProvider = animalsProvider;
    }

    @Override
    public AnimalRelations getNewInstanceFor(Animal animal) {
        List<Animal> allOtherAnimals = animalsProvider.getAnimals().stream()
                .filter(otherAnimal -> !animal.equals(otherAnimal))
                .collect(Collectors.toList());
        return new AnimalRelationsImpl(allOtherAnimals);
    }
}
