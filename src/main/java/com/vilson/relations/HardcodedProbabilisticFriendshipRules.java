package com.vilson.relations;

import com.vilson.animals.Animal;
import com.vilson.random.RandomProvider;

class HardcodedProbabilisticFriendshipRules implements ProbabilisticFriendshipRules {

    private final RandomProvider random;
    private final RelationsContainer relationsContainer;

    private final int maxNumberOfFriendsForGoodProbabilityToGetAndKeepFriends;
    private final int goodProbability;
    private final int badProbability;

    HardcodedProbabilisticFriendshipRules(RandomProvider random, RelationsContainer relationsContainer,
                                          ConfigurationForProbabilisticFriendshipRules configuration) {
        this.random = random;
        this.relationsContainer = relationsContainer;

        maxNumberOfFriendsForGoodProbabilityToGetAndKeepFriends =
                configuration.getMaxNumberOfFriendsForGoodProbabilityToGetFriends();
        goodProbability = configuration.getGoodProbability();
        badProbability = configuration.getBadProbability();
    }

    @Override
    public boolean possibleToStartFriendshipBetween(Animal initiator, Animal responder) {
        return accept(initiator) && accept(responder);
    }

    @Override
    public boolean wishesToEndAFriendship(Animal initiator) {
        return rejectFriendship(initiator);
    }

    private boolean accept(Animal animal) {
        int acceptancePercentage = acceptancePercentage(animal);
        return trueWithProbability(acceptancePercentage);
    }

    private boolean rejectFriendship(Animal animal) {
        int rejectionPercentage = rejectionPercentage(animal);
        return trueWithProbability(rejectionPercentage);
    }

    private int acceptancePercentage(Animal animal) {
        int numberOfFriends = relationsContainer.getNumberOfFriendsOf(animal);
        return numberOfFriends <= maxNumberOfFriendsForGoodProbabilityToGetAndKeepFriends ?
                goodProbability :
                badProbability;
    }

    private boolean trueWithProbability(int probabilityPercentage) {
        return zeroIsAlwaysFalseAnd100IsAlwaysTrue(probabilityPercentage);
    }

    private int rejectionPercentage(Animal animal) {
        int numberOfFriends = relationsContainer.getNumberOfFriendsOf(animal);
        return numberOfFriends > maxNumberOfFriendsForGoodProbabilityToGetAndKeepFriends ?
                goodProbability :
                badProbability;
    }

    private boolean zeroIsAlwaysFalseAnd100IsAlwaysTrue(int probabilityPercentage) {
        int randomFrom0To99 = random.provideRandomIntFromZeroToExcluded(100);
        return randomFrom0To99 < probabilityPercentage;
    }
}
