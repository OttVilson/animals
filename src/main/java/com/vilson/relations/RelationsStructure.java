package com.vilson.relations;

import com.vilson.animals.AnimalsProvider;
import com.vilson.random.RandomProvider;

public class RelationsStructure {

    public final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;
    public final FriendshipActions friendshipActions;
    public final FriendshipTable friendshipTable;

    public RelationsStructure(AnimalsProvider animalsProvider, RandomProvider randomProvider) {
        AnimalRelationsFactory animalRelationsFactory = new AnimalRelationsFactoryImpl(animalsProvider);
        potentialBestFriendsForLifeProvider =
                new HardcodedPotentialBestFriendsForLifeProvider(animalsProvider);
        RelationsContainer relationsContainer = new RelationsContainerImpl(animalsProvider,
                potentialBestFriendsForLifeProvider, animalRelationsFactory);
        ConfigurationForProbabilisticFriendshipRules configuration =
                new HardcodedConfigurationForProbabilisticFriendshipRules();
        ProbabilisticFriendshipRules probabilisticFriendshipRules =
                new HardcodedProbabilisticFriendshipRules(randomProvider, relationsContainer, configuration);
        friendshipActions = new FriendshipActionsImpl(animalsProvider, relationsContainer,
                probabilisticFriendshipRules, randomProvider);
        friendshipTable = new FriendshipTable(animalsProvider, relationsContainer);
    }
}
