package com.vilson.relations;

import com.vilson.animals.Animal;

interface ProbabilisticFriendshipRules {
    boolean possibleToStartFriendshipBetween(Animal initiator, Animal responder);

    boolean wishesToEndAFriendship(Animal animal);
}
