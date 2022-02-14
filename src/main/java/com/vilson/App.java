package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.relations.format.FriendshipsTableFormatter;

public class App {

    private AnimalsProvider animalsProvider;

    public static void main(String[] args) {

        Structure structure = new Structure();
        printOutAllAnimalsWithTheirAttributes(structure);
        FriendshipsTableFormatter tableFormatter = new FriendshipsTableFormatter(
                structure.animalsProvider.getAnimals().size(),
                structure.animalsProvider.getAnimals().stream().map(Animal::getName)
                        .map(String::length).max(Integer::compare).get());

        for (int i = 0; i < 10; i++) {
            structure.day.processDayN(i + 1);
            structure.output.forwardMessage(
                    tableFormatter.getFormattedTable(
                            structure.friendshipTable.generateSquareTableWithAtLeastOneCell()));
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
