package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.generics.FlaggedOrderedPair;
import com.vilson.generics.UnorderedPair;

import java.util.List;

public interface FriendshipActions {

    public List<UnorderedPair<Animal>> roundOfLosingFriends();

    public List<FlaggedOrderedPair<Animal>> roundOfGainingFriends();
}
