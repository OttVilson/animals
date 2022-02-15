package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.UnorderedPair;

import java.util.*;
import java.util.stream.Collectors;

class HardcodedPotentialBestFriendsForLifeProvider implements PotentialBestFriendsForLifeProvider {
    private final Set<UnorderedPair<Animal>> potentialBestFriends;

    HardcodedPotentialBestFriendsForLifeProvider(AnimalsProvider animalsProvider) {
        this.potentialBestFriends = getInitializedPotentialBestFriends(animalsProvider);
    }

    @Override
    public boolean arePotentiallyBestFriends(UnorderedPair<Animal> pairOfAnimals) {
        return potentialBestFriends.contains(pairOfAnimals);
    }

    @Override
    public String outputPotentialBestFriends() {
        return formatMessage();
    }

    private Set<UnorderedPair<Animal>> getInitializedPotentialBestFriends(AnimalsProvider animalsProvider) {
        List<Animal> animals = new ArrayList<>(animalsProvider.getAnimals());
        Set<UnorderedPair<Animal>> potentialBestFriends = new HashSet<>();
        fillSet(potentialBestFriends, animals);
        return potentialBestFriends;
    }

    private String formatMessage() {
        return potentialBestFriends.stream()
                .map(this::getNamesFromAnimalPair)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void fillSet(Set<UnorderedPair<Animal>> potentialBestFriends, List<Animal> animals) {
        potentialBestFriends.add(composePair("Rex", "Tom", animals));
        potentialBestFriends.add(composePair("Max", "Jay", animals));
        potentialBestFriends.add(composePair("Zoe", "Ada", animals));
        potentialBestFriends.add(composePair("Meg", "Lis", animals));
        potentialBestFriends.add(composePair("Emi", "Lua", animals));
        potentialBestFriends.add(composePair("Mac", "Alf", animals));
    }

    private String getNamesFromAnimalPair(UnorderedPair<Animal> pair) {
        return pair.getComponents().stream().map(Animal::getName).collect(Collectors.joining(" and "));
    }

    private UnorderedPair<Animal> composePair(String nameOfOne, String nameOfOther, List<Animal> animals) {
        Animal one = findAnimal(nameOfOne, animals);
        Animal other = findAnimal(nameOfOther, animals);

        return new UnorderedPair<>(one, other);
    }

    private Animal findAnimal(String name, List<Animal> animals) {
        Optional<Animal> animal = animals.stream().filter(a -> a.getName().equals(name)).findAny();
        animal.orElseThrow(
                () -> new IllegalArgumentException("The animal (" + name + ") wasn't present at all"));

        return animal.get();
    }
}
