package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.generics.FlaggedOrderedPair;
import com.vilson.generics.UnorderedPair;

import java.util.List;

public interface FriendshipActions {

    List<UnorderedPair<Animal>> roundOfLosingFriends();

    List<FlaggedOrderedPair<Animal>> roundOfGainingFriends();
}
