package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.UnorderedPair;

import java.util.*;
import java.util.function.BiConsumer;

class RelationsContainerImpl implements RelationsContainer {

    private final Map<Animal, AnimalRelations> animalsAndTheirRelations;
    private final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;
    private final Set<UnorderedPair<Animal>> allFriendships;

    RelationsContainerImpl(AnimalsProvider animalsProvider,
                           PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider,
                           AnimalRelationsFactory animalRelationsFactory) {
        animalsAndTheirRelations =
                initializeAnimalsAndTheirRelations(animalsProvider, animalRelationsFactory);
        this.potentialBestFriendsForLifeProvider = potentialBestFriendsForLifeProvider;
        allFriendships = new HashSet<>();
    }

    @Override
    public List<Animal> getAllFriendsOf(Animal animal) {
        validateThatInputAnimalBelongsToTheOriginalOnes(animal);
        return animalsAndTheirRelations.get(animal).getAllFriends();
    }

    @Override
    public List<Animal> getDisposableFriendsOf(Animal animal) {
        validateThatInputAnimalBelongsToTheOriginalOnes(animal);
        return animalsAndTheirRelations.get(animal).getFriendsOtherThanBestFriend();
    }

    @Override
    public List<Animal> getNonFriendsOf(Animal animal) {
        validateThatInputAnimalBelongsToTheOriginalOnes(animal);
        return animalsAndTheirRelations.get(animal).getCurrentNonFriends();
    }

    @Override
    public int getNumberOfFriendsOf(Animal animal) {
        validateThatInputAnimalBelongsToTheOriginalOnes(animal);
        return animalsAndTheirRelations.get(animal).getNumberOfFriends();
    }

    @Override
    public void addFriendship(UnorderedPair<Animal> toBeFriends) {
        validateThatBothAnimalsInInputPairBelongToTheOriginalOnes(toBeFriends);
        if (potentialBestFriendsForLifeProvider.arePotentiallyBestFriends(toBeFriends))
            performActions(toBeFriends, AnimalRelations::addBestFriend, Collection::add);
        else
            performActions(toBeFriends, AnimalRelations::addFriend, Collection::add);
    }

    @Override
    public void removeFriendship(UnorderedPair<Animal> formerFriends) {
        validateThatBothAnimalsInInputPairBelongToTheOriginalOnes(formerFriends);
        performActions(formerFriends, AnimalRelations::removeFriend, Collection::remove);
    }

    @Override
    public boolean areFriends(UnorderedPair<Animal> pair) {
        return allFriendships.contains(pair);
    }

    private void performActions(UnorderedPair<Animal> pair,
                                BiConsumer<AnimalRelations, Animal> actionOnIndividualLevel,
                                BiConsumer<Set<UnorderedPair<Animal>>, UnorderedPair<Animal>>
                                        actionOnJointCache) {
        symmetrizeBiConsumerUse(pair, actionOnIndividualLevel);
        actionOnJointCache.accept(allFriendships, pair);
    }

    private Map<Animal, AnimalRelations> initializeAnimalsAndTheirRelations(
            AnimalsProvider animalsProvider, AnimalRelationsFactory animalRelationsFactory) {
        Map<Animal, AnimalRelations> animalsAndTheirRelations = new HashMap<>();
        List<Animal> allAnimals = animalsProvider.getAnimals();

        for (Animal animal : allAnimals)
            animalsAndTheirRelations.put(animal, animalRelationsFactory.getNewInstanceFor(animal));
        return animalsAndTheirRelations;
    }

    private AnimalRelations getRelationsFor(Animal animal) {
        return animalsAndTheirRelations.get(animal);
    }

    private void validateThatInputAnimalBelongsToTheOriginalOnes(Animal animal) {
        if (!animalsAndTheirRelations.containsKey(animal))
            throw new IllegalArgumentException("The animal (" + animal + ") hasn't been provided.");
    }

    private void validateThatBothAnimalsInInputPairBelongToTheOriginalOnes(UnorderedPair<Animal> both) {
        both.getComponents().forEach(this::validateThatInputAnimalBelongsToTheOriginalOnes);
    }

    private void symmetrizeBiConsumerUse(UnorderedPair<Animal> pair,
                                         BiConsumer<AnimalRelations, Animal> relationChange) {
        List<Animal> both = pair.getComponents();
        Animal one = both.get(0);
        Animal other = both.get(1);
        relationChange.accept(getRelationsFor(one), other);
        relationChange.accept(getRelationsFor(other), one);
    }
}
