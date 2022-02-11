package com.vilson.friends;

import com.vilson.animals.Animal;

import java.util.LinkedList;
import java.util.List;

class AnimalRelations {

    private final List<Animal> allOtherAnimals;
    private int numberOfFriendsAkaFirstNonFriend = 0;
    private boolean isBestFriendSet = false;

    AnimalRelations(List<Animal> allOtherAnimals) {
        this.allOtherAnimals = new LinkedList<>(allOtherAnimals);
    }

    void addFriend(Animal animal) {
        addFriendToTheEndOfFriendsList(animal);
    }

    void addBestFriend(Animal animal) {
        validateAddingBestFriend(animal);
        addFriendToTheEndOfFriendsList(animal);
        moveBestFriendFromLastFriendToFirst();
        isBestFriendSet = true;
    }

    void removeFriend(Animal animal) {
        int index = allOtherAnimals.indexOf(animal);
        validateIndexForRemovingFriend(index, animal);

        allOtherAnimals.remove(index);
        allOtherAnimals.add(--numberOfFriendsAkaFirstNonFriend, animal);
    }

    int getNumberOfFriends() {
        return numberOfFriendsAkaFirstNonFriend;
    }

    List<Animal> getFriendsOtherThanBestFriend() {
        int start = getStartIndexForDisposableFriends();
        return allOtherAnimals.subList(start, numberOfFriendsAkaFirstNonFriend);
    }

    List<Animal> getAllFriends() {
        return allOtherAnimals.subList(0, numberOfFriendsAkaFirstNonFriend);
    }

    List<Animal> getCurrentNonFriends() {
        return allOtherAnimals.subList(numberOfFriendsAkaFirstNonFriend, allOtherAnimals.size());
    }

    private void validateAddingBestFriend(Animal animal) {
        if (isBestFriendSet)
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

        if (index >= numberOfFriendsAkaFirstNonFriend)
            throw new IllegalArgumentException("The animal (" + animal + ") wasn't a friend to start with.");
    }

    private void moveBestFriendFromLastFriendToFirst() {
        Animal bestFriend = allOtherAnimals.remove(numberOfFriendsAkaFirstNonFriend - 1);
        allOtherAnimals.add(0, bestFriend);
    }

    private int getStartIndexForDisposableFriends() {
        return isBestFriendSet ? 1 : 0;
    }
}
