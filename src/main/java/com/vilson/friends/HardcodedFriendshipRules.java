package com.vilson.friends;

import com.vilson.animals.Animal;
import com.vilson.random.RandomProvider;

public class HardcodedFriendshipRules implements FriendshipRules {

    private final RandomProvider random;
    private final RelationsContainer relationsContainer;

    public HardcodedFriendshipRules(RandomProvider random, RelationsContainer relationsContainer) {
        this.random = random;
        this.relationsContainer = relationsContainer;
    }

    @Override
    public boolean possibleToStartFriendshipBetween(Animal initiator, Animal responder) {
        return accept(initiator) && accept(responder);
    }

    @Override
    public boolean wishesToEndAFriendship(Animal initiator) {
        return reject(initiator);
    }

    private boolean trueWithProbability(int probabilityPercentage) {
        return zeroIsAlwaysFalseAnd100IsAlwaysTrue(probabilityPercentage);
    }

    private boolean zeroIsAlwaysFalseAnd100IsAlwaysTrue(int probabilityPercentage) {
        int randomFrom0To99 = random.provideRandomIntFromZeroToExcluded(100);
        return randomFrom0To99 < probabilityPercentage;
    }

    private boolean accept(Animal animal) {
        int acceptancePercentage = acceptancePercentage(animal);
        return trueWithProbability(acceptancePercentage);
    }

    private boolean reject(Animal animal) {
        int rejectionPercentage = rejectionPercentage(animal);
        return trueWithProbability(rejectionPercentage);
    }

    private int acceptancePercentage(Animal animal) {
        int numberOfFriends = relationsContainer.getNumberOfFriendsOf(animal);
        return numberOfFriends >= 3 ? 10 : 90;
    }

    private int rejectionPercentage(Animal animal) {
        int numberOfFriends = relationsContainer.getNumberOfFriendsOf(animal);
        return numberOfFriends >= 3 ? 90 : 10;
    }
}
