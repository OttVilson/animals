package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;

import java.util.List;

public class App {

    private AnimalsProvider animalsProvider;

    public static void main(String[] args) {

        Structure structure = new Structure();
        printOutAllAnimalsWithTheirAttributes(structure);

        for (int i = 0; i < 10; i++) {
            structure.day.processDayN(i + 1);
            for (List<String> row : structure.friendshipTable.generateTable())
                structure.output.forwardMessage(row.toString());
        }

        structure.output.forwardMessage("The app finished");
    }

    private static void printOutAllAnimalsWithTheirAttributes(Structure structure) {
        structure.output.forwardMessage("Print out all animals with their attributes:");

        for (Animal animal : structure.animalsProvider.getAnimals())
            structure.output.forwardTabbedMessage(animal.toString());

        structure.potentialBestFriendsForLifeProvider.outputPotentialBestFriends();
    }
}
