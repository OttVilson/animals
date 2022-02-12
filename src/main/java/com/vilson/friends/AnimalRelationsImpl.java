package com.vilson.friends;

import com.vilson.animals.Animal;

import java.util.LinkedList;
import java.util.List;

class AnimalRelationsImpl implements AnimalRelations {

    private final List<Animal> allOtherAnimals;
    private int numberOfFriendsAkaFirstNonFriend = 0;
    private boolean bestFriendIsSet = false;

    private static int BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0 = 0;

    AnimalRelationsImpl(List<Animal> allOtherAnimals) {
        this.allOtherAnimals = new LinkedList<>(allOtherAnimals);
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
        int index = allOtherAnimals.indexOf(animal);
        validateIndexForRemovingFriend(index, animal);

        allOtherAnimals.remove(index);
        allOtherAnimals.add(--numberOfFriendsAkaFirstNonFriend, animal);
    }

    public int getNumberOfFriends() {
        return numberOfFriendsAkaFirstNonFriend;
    }

    public List<Animal> getFriendsOtherThanBestFriend() {
        int start = getStartIndexByExcludingBestFriendAtIndex0IfPresent();
        return allOtherAnimals.subList(start, numberOfFriendsAkaFirstNonFriend);
    }

    public List<Animal> getAllFriends() {
        return allOtherAnimals.subList(0, numberOfFriendsAkaFirstNonFriend);
    }

    public List<Animal> getCurrentNonFriends() {
        return allOtherAnimals.subList(numberOfFriendsAkaFirstNonFriend, allOtherAnimals.size());
    }

    private void validateAddingBestFriend(Animal animal) {
        if (bestFriendIsSet)
            throw new IllegalArgumentException("Best friend has already been set, and pushing (" + animal +
                    ") is wrong.");
    }

    private void addFriendToTheEndOfFriendsList(Animal animal) {
        int index = allOtherAnimals.indexOf(animal);
        validateIndexForAddingFriend(index, animal);

        allOtherAnimals.remove(index);
        allOtherAnimals.add(numberOfFriendsAkaFirstNonFriend++, animal);
    }

    private void validateIndexForAddingFriend(int index, Animal animal) {
        throwIfAnimalNotPresent(index, animal);

        if (index < numberOfFriendsAkaFirstNonFriend)
            throw new IllegalArgumentException("The animal (" + animal + ") is already in the list of friends.");
    }

    private void throwIfAnimalNotPresent(int index, Animal animal) {
        if (index < 0)
            throw new IllegalArgumentException("The animal (" + animal + ") wasn't provided");
    }

    private void validateIndexForRemovingFriend(int index, Animal animal) {
        throwIfAnimalNotPresent(index, animal);
        throwIfTryingToRemoveBestFriend(index);

        if (index >= numberOfFriendsAkaFirstNonFriend)
            throw new IllegalArgumentException("The animal (" + animal + ") wasn't a friend to start with.");
    }

    private void throwIfTryingToRemoveBestFriend(int index) {
        if (bestFriendIsSet && index == BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0)
            throw new IllegalArgumentException("Trying to remove the best friend (" +
                    allOtherAnimals.get(BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0) + ").");
    }

    private void moveBestFriendFromLastFriendToFirst() {
        Animal bestFriend = allOtherAnimals.remove(numberOfFriendsAkaFirstNonFriend - 1);
        allOtherAnimals.add(BEST_FRIEND_IF_PRESENT_IS_POSITIONED_AT_INDEX_0, bestFriend);
    }

    private int getStartIndexByExcludingBestFriendAtIndex0IfPresent() {
        return bestFriendIsSet ? 1 : 0;
    }
}
