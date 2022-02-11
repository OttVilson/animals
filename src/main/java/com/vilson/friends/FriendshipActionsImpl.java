package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.FlaggedOrderedPair;
import com.vilson.generics.UnorderedPair;

import java.util.ArrayList;
import java.util.List;

public class FriendshipActionsImpl implements FriendshipActions {

    private final AnimalsProvider animalsProvider;

    public FriendshipActionsImpl(AnimalsProvider animalsProvider) {
        this.animalsProvider = animalsProvider;
    }

    @Override
    public List<UnorderedPair<Animal>> roundOfLosingFriends() {
        List<Animal> animals = animalsProvider.getAnimals();
        List<UnorderedPair<Animal>> broken = new ArrayList<>();
        broken.add(new UnorderedPair<>(animals.get(0), animals.get(1)));
        broken.add(new UnorderedPair<>(animals.get(2), animals.get(3)));
        return broken;
    }

    @Override
    public List<FlaggedOrderedPair<Animal>> roundOfGainingFriends() {
        List<Animal> animals = animalsProvider.getAnimals();
        List<FlaggedOrderedPair<Animal>> attempts = new ArrayList<>();
        attempts.add(new FlaggedOrderedPair<>(animals.get(0), animals.get(1), true));
        attempts.add(new FlaggedOrderedPair<>(animals.get(2), animals.get(3), false));

        return attempts;
    }
}
