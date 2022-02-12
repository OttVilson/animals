package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.FlaggedOrderedPair;
import com.vilson.generics.UnorderedPair;
import com.vilson.random.RandomProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FriendshipActionsImpl implements FriendshipActions {

    private final List<Animal> animals;
    private final RandomProvider<Animal> random;


    public FriendshipActionsImpl(AnimalsProvider animalsProvider, RandomProvider<Animal> random) {
        this.animals = animalsProvider.getAnimals();
        this.random = random;
    }

    @Override
    public List<UnorderedPair<Animal>> roundOfLosingFriends() {
        return collectResultfulAttempts(this::friendshipBreakAttempt);
    }

    @Override
    public List<FlaggedOrderedPair<Animal>> roundOfGainingFriends() {
        return collectResultfulAttempts(this::friendshipStartAttempt);
    }

    private <T> List<T> collectResultfulAttempts(Function<Animal, Optional<T>> attemptFunction) {
        List<Optional<T>> attempts = new ArrayList<>();
        random.shuffle(animals);
        for (Animal animal : animals)
            attempts.add(attemptFunction.apply(animal));

        return forwardThoseThatArePresent(attempts);
    }

    private Optional<UnorderedPair<Animal>> friendshipBreakAttempt(Animal animal) {
        return Optional.empty();
    }

    private Optional<FlaggedOrderedPair<Animal>> friendshipStartAttempt(Animal animal) {
        return Optional.empty();
    }

    private <T> List<T> forwardThoseThatArePresent(List<Optional<T>> attempts) {
        return attempts.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
