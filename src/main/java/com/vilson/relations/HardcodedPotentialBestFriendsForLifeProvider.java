package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.UnorderedPair;
import com.vilson.output.Output;

import java.util.*;
import java.util.stream.Collectors;

class HardcodedPotentialBestFriendsForLifeProvider implements PotentialBestFriendsForLifeProvider {
    private final Set<UnorderedPair<Animal>> potentialBestFriends;
    private final Output output;

    HardcodedPotentialBestFriendsForLifeProvider(AnimalsProvider animalsProvider, Output output) {
        this.potentialBestFriends = getInitializedPotentialBestFriends(animalsProvider);
        this.output = output;
    }

    @Override
    public boolean arePotentiallyBestFriends(UnorderedPair<Animal> pairOfAnimals) {
        return potentialBestFriends.contains(pairOfAnimals);
    }

    @Override
    public void outputPotentialBestFriends() {
        output.forwardMessage("The potential best friends for life are:");
        for (UnorderedPair<Animal> pair : potentialBestFriends)
            output.forwardTabbedMessage(getNamesFromAnimalPair(pair));
    }

    private Set<UnorderedPair<Animal>> getInitializedPotentialBestFriends(AnimalsProvider animalsProvider) {
        List<Animal> animals = new LinkedList<>(animalsProvider.getAnimals());
        Set<UnorderedPair<Animal>> potentialBestFriends = new HashSet<>();
        fillSet(animals, potentialBestFriends);
        return potentialBestFriends;
    }

    private void fillSet(List<Animal> animals, Set<UnorderedPair<Animal>> potentialBestFriends) {
        potentialBestFriends.add(composePair(animals, "Rex", "Tom"));
        potentialBestFriends.add(composePair(animals, "Max", "Jay"));
        potentialBestFriends.add(composePair(animals, "Zoe", "Ada"));
        potentialBestFriends.add(composePair(animals, "Meg", "Lis"));
        potentialBestFriends.add(composePair(animals, "Emi", "Lua"));
        potentialBestFriends.add(composePair(animals, "Mac", "Alf"));
    }

    private UnorderedPair<Animal> composePair(List<Animal> animals,
                                              String nameOfOne, String nameOfOther) {
        Animal one = findAnimal(animals, nameOfOne);
        Animal other = findAnimal(animals, nameOfOther);

        return new UnorderedPair<>(one, other);
    }

    private Animal findAnimal(List<Animal> animals, String name) {
        Optional<Animal> animal = animals.stream().filter(a -> a.getName().equals(name)).findAny();
        animal.orElseThrow(
                () -> new IllegalArgumentException("The animal (" + name + ") wasn't present at all"));

        return animal.get();
    }

    private String getNamesFromAnimalPair(UnorderedPair<Animal> pair) {
        return pair.getComponents().stream().map(Animal::getName).collect(Collectors.joining(" and "));
    }
}
