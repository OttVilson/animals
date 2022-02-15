package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.generics.UnorderedPair;

import java.util.List;

interface RelationsContainer {
    List<Animal> getFriendsOtherThanBestFriendOf(Animal animal);

    List<Animal> getNonFriendsOf(Animal animal);

    int getNumberOfFriendsOf(Animal animal);

    void addFriendship(UnorderedPair<Animal> toBeFriends);

    void removeFriendship(UnorderedPair<Animal> formerFriends);

    boolean areFriends(UnorderedPair<Animal> pair);
}
