package com.vilson;

import com.vilson.animals.AnimalsProvider;
import com.vilson.animals.HardcodedAnimalsProvider;
import com.vilson.day.Day;
import com.vilson.day.Lunch;
import com.vilson.friends.*;
import com.vilson.output.ConsoleOutput;
import com.vilson.output.Output;
import com.vilson.random.RandomProvider;
import com.vilson.random.RandomProviderImpl;

class Structure {

    final AnimalsProvider animalsProvider;
    final Output output;
    final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;
    final RelationsContainer relationsContainer;
    final RandomProvider random;
    final FriendshipRules friendshipRules;
    final FriendshipActions friendshipActions;
    final FriendshipTable friendshipTable;
    final Lunch lunch;
    final Day day;

    Structure() {
        animalsProvider = new HardcodedAnimalsProvider();
        output = new ConsoleOutput();
        potentialBestFriendsForLifeProvider = new HardcodedPotentialBestFriendsForLifeProvider(animalsProvider, output);
        relationsContainer = new RelationsContainerImpl(animalsProvider, potentialBestFriendsForLifeProvider);

        random = new RandomProviderImpl();
        friendshipRules = new HardcodedFriendshipRules(random, relationsContainer);
        friendshipActions = new FriendshipActionsImpl(animalsProvider, random, relationsContainer,
                friendshipRules);
        friendshipTable = new FriendshipTable(animalsProvider, relationsContainer);
        lunch = new Lunch(animalsProvider);
        day = new Day(friendshipActions, lunch, output);
    }
}
