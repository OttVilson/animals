package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.generics.UnorderedPair;

import java.util.List;

public interface RelationsContainer {
    List<Animal> getAllFriendsOf(Animal animal);

    List<Animal> getDisposableFriendsOf(Animal animal);

    int getNumberOfFriendsOf(Animal animal);

    void addFriendship(UnorderedPair<Animal> toBeFriends);

    void removeFriendship(UnorderedPair<Animal> formerFriends);
}
