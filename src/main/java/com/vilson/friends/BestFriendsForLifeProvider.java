package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.generics.UnorderedPair;

public interface BestFriendsForLifeProvider {
    public boolean arePotentiallyBestFriends(UnorderedPair<Animal> pairOfAnimals);

    public void printPotentialBestFriends();
}
