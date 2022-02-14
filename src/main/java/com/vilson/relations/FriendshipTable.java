package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vilson.generics.UnorderedPair.formUnorderedPairWith;

public class FriendshipTable {

    private final static String CHOSEN = "X";
    private final static String EMPTY_CELL = "";

    private final List<Animal> animals;
    private final RelationsContainer relationsContainer;

    FriendshipTable(AnimalsProvider animalsProvider, RelationsContainer relationsContainer) {
        this.animals = animalsProvider.getAnimals();
        animals.sort(Comparator.comparing(Animal::getName));
        this.relationsContainer = relationsContainer;
    }

    public List<List<String>> generateSquareTableWithAtLeastOneCell() {
        List<List<String>> rows = new ArrayList<>();
        rows.add(getFirstRow());
        
        animals.stream().map(this::getRowFor).forEachOrdered(rows::add);
        return rows;
    }

    private List<String> getFirstRow() {
        Stream<String> animalNames = animals.stream().map(Animal::getName);
        return startStreamWith(EMPTY_CELL, animalNames)
                .collect(Collectors.toList());
    }

    private List<String> getRowFor(Animal animal) {
        Stream<String> relations = animals.stream()
                .map(formUnorderedPairWith(animal))
                .map(relationsContainer::areFriends)
                .map(this::flaggedCellOutput);
        return startStreamWith(animal.getName(), relations).collect(Collectors.toList());
    }

    private String flaggedCellOutput(boolean flag) {
        return flag ? CHOSEN : EMPTY_CELL;
    }

    private <T> Stream<T> startStreamWith(T startWithElement, Stream<T> stream) {
        return Stream.concat(Stream.of(startWithElement), stream);
    }
}
