package com.vilson;

import com.vilson.animals.Animal;

public class App {

    public static void main(String[] args) {
        Structure structure = new Structure();
        printOutAllAnimalsWithTheirAttributes(structure);
        processAllTheDays(structure);
        structure.output.forwardMessage("The app finished");
    }

    private static void printOutAllAnimalsWithTheirAttributes(Structure structure) {
        structure.output.forwardMessage("Print out all animals with their attributes:");
        for (Animal animal : structure.animalsProvider.getAnimals())
            structure.output.forwardIndentedMessage(animal.toString());

        structure.output.forwardMessage("The potential best friends for life are:");
        structure.output.forwardIndentedMessage(
                structure.potentialBestFriendsForLifeProvider.outputPotentialBestFriends());
    }

    private static void processAllTheDays(Structure structure) {
        for (int i = 1; i <= 10; i++) {
            structure.pause.untilEnter(i);
            structure.day.processDayN(i);
            structure.output.forwardMessage("");
        }
    }
}
