package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.generics.*;
import com.vilson.random.RandomProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

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
        return collectResultfulAttempts(new AttemptMethodReferences<>(
                this::friendshipBreakAttempt,
                this.relationsContainer::getFriendsOtherThanBestFriendOf,
                ActionCountsAgainstDailyQuotaOfBothMembersInPair::getIterator));
    }

    @Override
    public List<FlaggedOrderedPair<Animal>> roundOfGainingFriends() {
        return collectResultfulAttempts(new AttemptMethodReferences<>(
                this::friendshipStartAttempt,
                this.relationsContainer::getNonFriendsOf,
                ActionCountsAgainstDailyQuotaOfOnlyInitiator::getIterator));
    }

    private <T extends Pair<Animal>> List<T> collectResultfulAttempts(AttemptMethodReferences<T> refs) {

        random.shuffle(animals);
        IteratorWithFeedback<Animal> iterator = refs.iteratorFactory.apply(animals);
        List<T> results = new ArrayList<>();

        while (iterator.hasNext())
            iteratorRound(refs, iterator, results);

        return results;
    }

    private Optional<UnorderedPair<Animal>> friendshipBreakAttempt(Animal animal, List<Animal> whichList) {
        if (probabilisticFriendshipRules.wishesToEndAFriendship(animal)) {
            return processFriendshipActionAndGetResult(whichList,
                    animalFromList -> removeAndReturnFormerFriendshipBetween(animal, animalFromList));
        } else
            return Optional.empty();
    }

    private Optional<FlaggedOrderedPair<Animal>> friendshipStartAttempt(Animal animal, List<Animal> whichList) {
        return processFriendshipActionAndGetResult(whichList,
                animalFromList -> attemptFriendshipBetweenAndAnnounceResult(animal, animalFromList));
    }

    private <T extends Pair<Animal>> void iteratorRound(AttemptMethodReferences<T> refs,
                                                        IteratorWithFeedback<Animal> iterator, List<T> results) {
        Animal initiator = iterator.next();
        List<Animal> animalsToChooseFrom = getAnimalsFromWhomToChoose(refs, initiator, iterator);
        Optional<T> optionalPair = refs.attemptFunction.apply(initiator, animalsToChooseFrom);
        optionalPair.ifPresent(iterator::feedback);
        optionalPair.ifPresent(results::add);
    }

    private <T> Optional<T> processFriendshipActionAndGetResult(List<Animal> whichList, Function<Animal, T> action) {
        Optional<Animal> other = getRandomOtherAnimalFrom(whichList);
        return other.map(action);
    }

    private UnorderedPair<Animal> removeAndReturnFormerFriendshipBetween(Animal one, Animal other) {
        UnorderedPair<Animal> formerFriends = new UnorderedPair<>(one, other);
        relationsContainer.removeFriendship(formerFriends);
        return formerFriends;
    }

    private FlaggedOrderedPair<Animal> attemptFriendshipBetweenAndAnnounceResult(Animal initiator, Animal responder) {
        boolean friendshipAttemptSuccessful =
                probabilisticFriendshipRules.possibleToStartFriendshipBetween(initiator, responder);
        if (friendshipAttemptSuccessful)
            relationsContainer.addFriendship(new UnorderedPair<>(initiator, responder));

        return new FlaggedOrderedPair<>(initiator, responder, friendshipAttemptSuccessful);
    }

    private <T extends Pair<Animal>> List<Animal> getAnimalsFromWhomToChoose(AttemptMethodReferences<T> refs,
                                                                             Animal initiator,
                                                                             IteratorWithFeedback<Animal> iterator) {
        List<Animal> animalsToChooseFrom = refs.pickRandomAnimalFromWhichListOf.apply(initiator);
        iterator.removeThoseNotParticipatingAnymoreFromList(animalsToChooseFrom);
        return animalsToChooseFrom;
    }

    private Optional<Animal> getRandomOtherAnimalFrom(List<Animal> otherAnimals) {
        if (otherAnimals.isEmpty()) return Optional.empty();

        int randomIndex = random.provideRandomIntFromZeroToExcluded(otherAnimals.size());
        return Optional.of(otherAnimals.get(randomIndex));
    }

    private static class AttemptMethodReferences<T extends Pair<Animal>> {
        private final BiFunction<Animal, List<Animal>, Optional<T>> attemptFunction;
        private final Function<Animal, List<Animal>> pickRandomAnimalFromWhichListOf;
        private final Function<List<Animal>, IteratorWithFeedback<Animal>> iteratorFactory;

        AttemptMethodReferences(
                BiFunction<Animal, List<Animal>, Optional<T>> attemptFunction,
                Function<Animal, List<Animal>> pickRandomAnimalFromWhichListOf,
                Function<List<Animal>, IteratorWithFeedback<Animal>> iteratorFactory) {
            this.attemptFunction = attemptFunction;
            this.pickRandomAnimalFromWhichListOf = pickRandomAnimalFromWhichListOf;
            this.iteratorFactory = iteratorFactory;
        }
    }
}
