package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.UnorderedPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class FriendshipsContainerImpl implements FriendshipsContainer {

    private final Map<Animal, AnimalRelations> animalsAndTheirRelations;
    private final BestFriendsForLifeProvider bestFriendsForLifeProvider;

    public FriendshipsContainerImpl(AnimalsProvider animalsProvider,
                                    BestFriendsForLifeProvider bestFriendsForLifeProvider) {
        animalsAndTheirRelations = initializeAnimalsAndTheirRelations(animalsProvider);
        this.bestFriendsForLifeProvider = bestFriendsForLifeProvider;
    }

    @Override
    public List<Animal> getAllFriendsOf(Animal animal) {
        return animalsAndTheirRelations.get(animal).getAllFriends();
    }

    @Override
    public List<Animal> getDisposableFriendsOf(Animal animal) {
        return animalsAndTheirRelations.get(animal).getFriendsOtherThanBestFriend();
    }

    @Override
    public int getNumberOfFriendsOf(Animal animal) {
        return animalsAndTheirRelations.get(animal).getNumberOfFriends();
    }

    @Override
    public void addFriendship(UnorderedPair<Animal> toBeFriends) {
        BiConsumer<AnimalRelations, Animal> addFriend;

        if (bestFriendsForLifeProvider.arePotentiallyBestFriends(toBeFriends))
            addFriend = AnimalRelations::addBestFriend;
        else
            addFriend = AnimalRelations::addFriend;

        symmetrizeBiConsumerUse(toBeFriends, addFriend);
    }

    @Override
    public void removeFriendship(UnorderedPair<Animal> formerFriends) {
        BiConsumer<AnimalRelations, Animal> removeFriend = AnimalRelations::removeFriend;
        symmetrizeBiConsumerUse(formerFriends, removeFriend);
    }

    private Map<Animal, AnimalRelations> initializeAnimalsAndTheirRelations(AnimalsProvider animalsProvider) {
        Map<Animal, AnimalRelations> animalsAndTheirRelations = new HashMap<>();
        List<Animal> allAnimals = animalsProvider.getAnimals();
        for (Animal animal : allAnimals)
            animalsAndTheirRelations.put(animal, initializeAnimalRelations(animal, allAnimals));
        return animalsAndTheirRelations;
    }

    private AnimalRelations initializeAnimalRelations(Animal animal, List<Animal> allAnimals) {
        List<Animal> allOtherAnimals = new ArrayList<>(allAnimals);
        boolean animalItselfWasPresent = allOtherAnimals.removeIf(otherAnimal -> otherAnimal.equals(animal));
        if (!animalItselfWasPresent)
            throw new IllegalArgumentException("The animal (" + animal +
                    ") wasn't present in the list of animals: " + allAnimals);

        return new AnimalRelations(allOtherAnimals);
    }

    private AnimalRelations getRelationsFor(Animal animal) {
        if (animalsAndTheirRelations.containsKey(animal))
            return animalsAndTheirRelations.get(animal);
        else
            throw new IllegalArgumentException("The animal (" + animal + ") hasn't been provided");
    }

    private void symmetrizeBiConsumerUse(UnorderedPair<Animal> pair,
                                         BiConsumer<AnimalRelations, Animal> biConsumer) {
        List<Animal> both = pair.getComponents();
        Animal one = both.get(0);
        Animal other = both.get(1);
        biConsumer.accept(getRelationsFor(one), other);
        biConsumer.accept(getRelationsFor(other), one);
    }
}
