package com.vilson.friends;

import com.vilson.animals.Animal;

public interface FriendshipRules {
    boolean possibleToStartFriendshipBetween(Animal initiator, Animal responder);

    boolean wishesToEndAFriendship(Animal animal);
}
