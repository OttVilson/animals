package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.generics.UnorderedPair;

import java.util.List;

public interface FriendshipContainer {
    List<Animal> getFriendsOf(Animal animal);

    int getNumberOfFriendsOf(Animal animal);

    void addFriendship(UnorderedPair<Animal> toBeFriends);

    void removeFriendship(UnorderedPair<Animal> formerFriends);
}
