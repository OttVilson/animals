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

    FriendshipActionsImpl(AnimalsProvider animalsProvider, RelationsContainer relationsContainer,
                          ProbabilisticFriendshipRules probabilisticFriendshipRules, RandomProvider random) {
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
            return processFriendshipActionAndGetResult(this.relationsContainer::getFriendsOtherThanBestFriendOf, animal,
                    animalFromList -> removeAndReturnFormerFriendshipBetween(animal, animalFromList));
        } else
            return Optional.empty();
    }

    private Optional<FlaggedOrderedPair<Animal>> friendshipStartAttempt(Animal animal) {
        return processFriendshipActionAndGetResult(this.relationsContainer::getNonFriendsOf, animal,
                animalFromList -> attemptFriendshipAndAnnounceResultBetween(animal, animalFromList));
    }

    private <T> Optional<T> processFriendshipActionAndGetResult(Function<Animal, List<Animal>> whichListOf,
                                                                Animal animal, Function<Animal, T> action) {
        Optional<Animal> other = getRandomOtherAnimal(whichListOf, animal);
        return other.map(action);
    }

    private UnorderedPair<Animal> removeAndReturnFormerFriendshipBetween(Animal one, Animal other) {
        UnorderedPair<Animal> formerFriends = new UnorderedPair<>(one, other);
        relationsContainer.removeFriendship(formerFriends);
        return formerFriends;
    }

    private FlaggedOrderedPair<Animal> attemptFriendshipAndAnnounceResultBetween(Animal initiator, Animal responder) {
        boolean friendshipAttemptSuccessful =
                probabilisticFriendshipRules.possibleToStartFriendshipBetween(initiator, responder);
        if (friendshipAttemptSuccessful)
            relationsContainer.addFriendship(new UnorderedPair<>(initiator, responder));

        return new FlaggedOrderedPair<>(initiator, responder, friendshipAttemptSuccessful);
    }

    private Optional<Animal> getRandomOtherAnimal(Function<Animal, List<Animal>> fromWhichListOf, Animal animal) {
        List<Animal> otherAnimals = fromWhichListOf.apply(animal);
        if (otherAnimals.isEmpty()) return Optional.empty();

        int randomIndex = random.provideRandomIntFromZeroToExcluded(otherAnimals.size());
        return Optional.of(otherAnimals.get(randomIndex));
    }
}
