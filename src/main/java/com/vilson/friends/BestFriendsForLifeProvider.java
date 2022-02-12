package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.generics.UnorderedPair;

public interface BestFriendsForLifeProvider {
    boolean arePotentiallyBestFriends(UnorderedPair<Animal> pairOfAnimals);

    void outputPotentialBestFriends();
}
