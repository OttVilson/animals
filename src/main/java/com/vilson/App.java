package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.animals.HardcodedAnimalsProvider;
import com.vilson.day.Day;
import com.vilson.day.Lunch;
import com.vilson.friends.FriendshipActions;
import com.vilson.friends.FriendshipActionsImpl;
import com.vilson.output.ConsoleOutput;

public class App {

    public static void main(String[] args) {
        AnimalsProvider animalsProvider = new HardcodedAnimalsProvider();
        for (Animal animal : animalsProvider.getAnimals()) {
            System.out.println(animal);
        }

        FriendshipActions friendshipActions = new FriendshipActionsImpl(animalsProvider);
        Day day = new Day(friendshipActions, new Lunch(animalsProvider), new ConsoleOutput());
        day.processDayN(5);
    }

}
