package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.animals.HardcodedAnimalsProvider;
import com.vilson.day.Day;
import com.vilson.day.Lunch;
import com.vilson.friends.BestFriendsForLifeProvider;
import com.vilson.friends.FriendshipActions;
import com.vilson.friends.FriendshipActionsImpl;
import com.vilson.friends.HardcodedBestFriendsForLifeProvider;
import com.vilson.output.ConsoleOutput;
import com.vilson.output.Output;
import com.vilson.random.RandomProvider;
import com.vilson.random.RandomProviderImpl;

public class App {

    public static void main(String[] args) {
        AnimalsProvider animalsProvider = new HardcodedAnimalsProvider();
        for (Animal animal : animalsProvider.getAnimals()) {
            System.out.println(animal);
        }

        Output output = new ConsoleOutput();
        RandomProvider<Animal> random = new RandomProviderImpl<>();


        FriendshipActions friendshipActions = new FriendshipActionsImpl(animalsProvider, random);
        Day day = new Day(friendshipActions, new Lunch(animalsProvider), output);
        day.processDayN(5);

        BestFriendsForLifeProvider bffl = new HardcodedBestFriendsForLifeProvider(animalsProvider, output);
        bffl.outputPotentialBestFriends();
    }

}
