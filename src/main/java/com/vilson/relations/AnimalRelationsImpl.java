package com.vilson.relations;

import com.vilson.animals.Animal;

import java.util.ArrayList;
import java.util.List;

class AnimalRelationsImpl implements AnimalRelations {

    private final List<Animal> allOtherAnimals;
    private int numberOfFriendsAkaFirstNonFriend = 0;
    private boolean bestFriendIsSet = false;

    private static int BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0 = 0;

    AnimalRelationsImpl(List<Animal> allOtherAnimals) {
        this.allOtherAnimals = new ArrayList<>(allOtherAnimals);
    }

    public void addFriend(Animal animal) {
        addFriendToTheEndOfFriendsList(animal);
    }

    public void addBestFriend(Animal animal) {
        validateAddingBestFriend(animal);
        addFriendToTheEndOfFriendsList(animal);
        moveBestFriendFromLastFriendToFirst();
        bestFriendIsSet = true;
    }

    public void removeFriend(Animal animal) {
        int index = allOtherAnimals.lastIndexOf(animal);
        validateIndexForRemovingFriend(index, animal);

        swapPositionsWithCurrentLastFriendAndDecreaseNumberOfFriendsCounterToConsiderAsNonFriend(index);
    }

    public int getNumberOfFriends() {
        return numberOfFriendsAkaFirstNonFriend;
    }

    public List<Animal> getFriendsOtherThanBestFriend() {
        int start = getStartIndexByExcludingBestFriendAtIndex0IfPresent();
        return new ArrayList<>(allOtherAnimals.subList(start, numberOfFriendsAkaFirstNonFriend));
    }

    public List<Animal> getCurrentNonFriends() {
        return new ArrayList<>(allOtherAnimals.subList(numberOfFriendsAkaFirstNonFriend, allOtherAnimals.size()));
    }

    private void addFriendToTheEndOfFriendsList(Animal animal) {
        int index = allOtherAnimals.indexOf(animal);
        validateIndexForAddingFriend(index, animal);

        swapPositionsWithCurrentFirstNonFriendAndIncreaseNumberOfFriendsCounterToConsiderAsFriend(index);
    }

    private void validateAddingBestFriend(Animal animal) {
        if (bestFriendIsSet)
            throw new IllegalArgumentException("Best friend has already been set, and pushing (" + animal +
                    ") is wrong.");
    }

    private void moveBestFriendFromLastFriendToFirst() {
        swapElements(BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0, numberOfFriendsAkaFirstNonFriend - 1);
    }

    private void validateIndexForRemovingFriend(int index, Animal animal) {
        throwIfAnimalNotPresent(index, animal);
        throwIfTryingToRemoveBestFriend(index);

        if (index >= numberOfFriendsAkaFirstNonFriend)
            throw new IllegalArgumentException("The animal (" + animal + ") wasn't a friend to start with.");
    }

    private void swapPositionsWithCurrentLastFriendAndDecreaseNumberOfFriendsCounterToConsiderAsNonFriend(int index) {
        swapElements(--numberOfFriendsAkaFirstNonFriend, index);
    }

    private int getStartIndexByExcludingBestFriendAtIndex0IfPresent() {
        return bestFriendIsSet ? 1 : 0;
    }

    private void validateIndexForAddingFriend(int index, Animal animal) {
        throwIfAnimalNotPresent(index, animal);

        if (index < numberOfFriendsAkaFirstNonFriend)
            throw new IllegalArgumentException("The animal (" + animal + ") is already in the list of friends.");
    }

    private void swapPositionsWithCurrentFirstNonFriendAndIncreaseNumberOfFriendsCounterToConsiderAsFriend(int index) {
        swapElements(numberOfFriendsAkaFirstNonFriend++, index);
    }

    private void swapElements(int first, int second) {
        if (first == second) return;
        Animal firstAnimal = allOtherAnimals.get(first);
        Animal secondAnimal = allOtherAnimals.get(second);
        allOtherAnimals.set(first, secondAnimal);
        allOtherAnimals.set(second, firstAnimal);
    }

    private void throwIfAnimalNotPresent(int index, Animal animal) {
        if (index < 0)
            throw new IllegalArgumentException("The animal (" + animal + ") wasn't provided");
    }

    private void throwIfTryingToRemoveBestFriend(int index) {
        if (bestFriendIsSet && index == BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0)
            throw new IllegalArgumentException("Trying to remove the best friend (" +
                    allOtherAnimals.get(BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0) + ").");
    }
}
