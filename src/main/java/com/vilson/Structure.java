package com.vilson;

import com.vilson.animals.AnimalsProvider;
import com.vilson.animals.HardcodedAnimalsProvider;
import com.vilson.day.Day;
import com.vilson.day.Lunch;
import com.vilson.output.ConsoleOutput;
import com.vilson.output.Output;
import com.vilson.random.RandomProvider;
import com.vilson.random.RandomProviderImpl;
import com.vilson.relations.FriendshipTable;
import com.vilson.relations.PotentialBestFriendsForLifeProvider;
import com.vilson.relations.RelationsStructure;

class Structure {

    final AnimalsProvider animalsProvider;
    final Output output;
    final Day day;

    final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;
    final FriendshipTable friendshipTable;

    Structure() {
        animalsProvider = new HardcodedAnimalsProvider();
        output = new ConsoleOutput();
        RandomProvider random = new RandomProviderImpl();

        RelationsStructure relations = new RelationsStructure(animalsProvider, random, output);
        potentialBestFriendsForLifeProvider = relations.potentialBestFriendsForLifeProvider;
        friendshipTable = relations.friendshipTable;

        Lunch lunch = new Lunch(animalsProvider);
        day = new Day(relations.friendshipActions, lunch, output);
    }
}
