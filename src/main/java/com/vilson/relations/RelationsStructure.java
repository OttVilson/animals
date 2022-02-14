package com.vilson.relations;

import com.vilson.animals.AnimalsProvider;
import com.vilson.output.Output;
import com.vilson.random.RandomProvider;

public class RelationsStructure {

    private final AnimalRelationsFactory animalRelationsFactory;
    private final RelationsContainer relationsContainer;
    private final ProbabilisticFriendshipRules probabilisticFriendshipRules;

    public final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;
    public final FriendshipActions friendshipActions;
    public final FriendshipTable friendshipTable;

    public RelationsStructure(AnimalsProvider animalsProvider, RandomProvider randomProvider, Output output) {
        animalRelationsFactory = new AnimalRelationsFactoryImpl(animalsProvider);
        potentialBestFriendsForLifeProvider =
                new HardcodedPotentialBestFriendsForLifeProvider(animalsProvider, output);
        relationsContainer = new RelationsContainerImpl(animalsProvider, potentialBestFriendsForLifeProvider,
                animalRelationsFactory);
        probabilisticFriendshipRules = new HardcodedProbabilisticFriendshipRules(randomProvider,
                relationsContainer);
        friendshipActions = new FriendshipActionsImpl(animalsProvider, randomProvider, relationsContainer,
                probabilisticFriendshipRules);
        friendshipTable = new FriendshipTable(animalsProvider, relationsContainer);
    }
}
