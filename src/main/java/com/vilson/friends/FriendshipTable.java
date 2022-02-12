package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.UnorderedPair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FriendshipTable {

    private final List<Animal> animals;
    private final RelationsContainer relationsContainer;

    public FriendshipTable(AnimalsProvider animalsProvider, RelationsContainer relationsContainer) {
        this.animals = animalsProvider.getAnimals();
        animals.sort(Comparator.comparing(Animal::getName));
        this.relationsContainer = relationsContainer;
    }

    public List<List<String>> generateTable() {
        Set<UnorderedPair<Animal>> allFriendships = gatherAllFriendships();

        List<List<String>> rows = new ArrayList<>();
        rows.add(getFirstRow());

        for (Animal animal : animals) {
            Stream<String> friendships = animals.stream()
                    .map(otherAnimal -> new UnorderedPair<>(otherAnimal, animal))
                    .map(allFriendships::contains)
                    .map(value -> value ? " X " : "   ");
            List<String> row = startStreamWith(friendships, animal.getName()).collect(Collectors.toList());
            rows.add(row);
        }

        return rows;
    }

    private List<String> getFirstRow() {
        Stream<String> animalNames = animals.stream().map(Animal::getName);
        return startStreamWith(animalNames, "   ")
                .collect(Collectors.toList());
    }

    private Set<UnorderedPair<Animal>> gatherAllFriendships() {
        return animals.stream()
                .map(this::getPairedAllFriendsOf)
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Stream<UnorderedPair<Animal>> getPairedAllFriendsOf(Animal animal) {
        return relationsContainer.getAllFriendsOf(animal).stream()
                .map(otherAnimal -> new UnorderedPair<>(animal, otherAnimal));
    }

    private <T> Stream<T> startStreamWith(Stream<T> stream, T startWithElement) {
        return Stream.concat(Stream.of(startWithElement), stream);
    }
}
