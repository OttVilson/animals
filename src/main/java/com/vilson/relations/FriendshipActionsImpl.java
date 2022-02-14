package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.FlaggedOrderedPair;
import com.vilson.generics.UnorderedPair;
import com.vilson.random.RandomProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class FriendshipActionsImpl implements FriendshipActions {

    private final List<Animal> animals;
    private final RelationsContainer relationsContainer;
    private final ProbabilisticFriendshipRules probabilisticFriendshipRules;
    private final RandomProvider random;

    FriendshipActionsImpl(AnimalsProvider animalsProvider, RandomProvider random,
                          RelationsContainer relationsContainer,
                          ProbabilisticFriendshipRules probabilisticFriendshipRules) {
        this.animals = animalsProvider.getAnimals();
        this.relationsContainer = relationsContainer;
        this.probabilisticFriendshipRules = probabilisticFriendshipRules;
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
        random.shuffle(animals);
        return animals.stream()
                .map(attemptFunction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<UnorderedPair<Animal>> friendshipBreakAttempt(Animal animal) {
        if (probabilisticFriendshipRules.wishesToEndAFriendship(animal)) {
            Optional<Animal> other =
                    getRandomOtherAnimal(this.relationsContainer::getDisposableFriendsOf, animal);

            return removeAndReturnFormerFriendshipIfOtherIsPresentElseReturnEmpty(animal, other);
        } else
            return Optional.empty();
    }

    private Optional<UnorderedPair<Animal>> removeAndReturnFormerFriendshipIfOtherIsPresentElseReturnEmpty(
            Animal animal, Optional<Animal> other) {
        if (other.isPresent()) {
            Animal otherAnimal = other.get();
            UnorderedPair<Animal> formerFriends = new UnorderedPair<>(animal, otherAnimal);
            relationsContainer.removeFriendship(formerFriends);
            return Optional.of(formerFriends);
        } else
            return Optional.empty();
    }

    private Optional<FlaggedOrderedPair<Animal>> friendshipStartAttempt(Animal animal) {
        Optional<Animal> other = getRandomOtherAnimal(this.relationsContainer::getNonFriendsOf, animal);

        if (other.isPresent()) {
            Animal otherAnimal = other.get();
            boolean friendshipAttemptSuccessful =
                    probabilisticFriendshipRules.possibleToStartFriendshipBetween(animal, otherAnimal);
            if (friendshipAttemptSuccessful)
                relationsContainer.addFriendship(new UnorderedPair<>(animal, otherAnimal));

            FlaggedOrderedPair<Animal> announcement =
                    new FlaggedOrderedPair<Animal>(animal, otherAnimal, friendshipAttemptSuccessful);
            return Optional.of(announcement);
        } else
            return Optional.empty();
    }

    private Optional<Animal> getRandomOtherAnimal(Function<Animal, List<Animal>> fromWhichListOf,
                                                  Animal animal) {
        List<Animal> otherAnimals = fromWhichListOf.apply(animal);
        if (otherAnimals.isEmpty()) return Optional.empty();

        int randomIndex = random.provideRandomIntFromZeroToExcluded(otherAnimals.size());
        return Optional.of(otherAnimals.get(randomIndex));
    }
}
