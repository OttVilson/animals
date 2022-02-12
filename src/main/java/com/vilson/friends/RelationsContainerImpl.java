package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.UnorderedPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class RelationsContainerImpl implements RelationsContainer {

    private final Map<Animal, AnimalRelationsImpl> animalsAndTheirRelations;
    private final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;

    public RelationsContainerImpl(AnimalsProvider animalsProvider,
                                  PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider) {
        animalsAndTheirRelations = initializeAnimalsAndTheirRelations(animalsProvider);
        this.potentialBestFriendsForLifeProvider = potentialBestFriendsForLifeProvider;
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
            symmetrizeBiConsumerUse(toBeFriends, AnimalRelationsImpl::addBestFriend);
        else
            symmetrizeBiConsumerUse(toBeFriends, AnimalRelationsImpl::addFriend);
    }

    @Override
    public void removeFriendship(UnorderedPair<Animal> formerFriends) {
        validateThatBothAnimalsInInputPairBelongToTheOriginalOnes(formerFriends);
        symmetrizeBiConsumerUse(formerFriends, AnimalRelationsImpl::removeFriend);
    }

    private Map<Animal, AnimalRelationsImpl> initializeAnimalsAndTheirRelations(AnimalsProvider animalsProvider) {
        Map<Animal, AnimalRelationsImpl> animalsAndTheirRelations = new HashMap<>();
        List<Animal> allAnimals = animalsProvider.getAnimals();
        for (Animal animal : allAnimals)
            animalsAndTheirRelations.put(animal, getInitializedAnimalRelations(animal, allAnimals));
        return animalsAndTheirRelations;
    }

    private AnimalRelationsImpl getInitializedAnimalRelations(Animal animal, List<Animal> allAnimals) {
        List<Animal> allOtherAnimals = new ArrayList<>(allAnimals);
        allOtherAnimals.removeIf(otherAnimal -> otherAnimal.equals(animal));

        return new AnimalRelationsImpl(allOtherAnimals);
    }

    private AnimalRelationsImpl getRelationsFor(Animal animal) {
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
                                         BiConsumer<AnimalRelationsImpl, Animal> biConsumer) {
        List<Animal> both = pair.getComponents();
        Animal one = both.get(0);
        Animal other = both.get(1);
        biConsumer.accept(getRelationsFor(one), other);
        biConsumer.accept(getRelationsFor(other), one);
    }
}
