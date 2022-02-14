package com.vilson;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.animals.HardcodedAnimalsProvider;
import com.vilson.day.Day;
import com.vilson.day.Lunch;
import com.vilson.output.ConsoleOutput;
import com.vilson.output.Output;
import com.vilson.random.RandomProvider;
import com.vilson.random.RandomProviderImpl;
import com.vilson.relations.PotentialBestFriendsForLifeProvider;
import com.vilson.relations.RelationsStructure;
import com.vilson.table.TableFormatter;

import java.util.Optional;

class Structure {

    final AnimalsProvider animalsProvider;
    final Output output;
    final Day day;

    final PotentialBestFriendsForLifeProvider potentialBestFriendsForLifeProvider;

    Structure() {
        animalsProvider = new HardcodedAnimalsProvider();
        output = new ConsoleOutput();
        RandomProvider random = new RandomProviderImpl();

        RelationsStructure relations = new RelationsStructure(animalsProvider, random, output);
        potentialBestFriendsForLifeProvider = relations.potentialBestFriendsForLifeProvider;

        Lunch lunch = new Lunch(animalsProvider);
        TableFormatter tableFormatter =
                new TableFormatter(getMaxNameLength(animalsProvider), getNumberOfParticipants(animalsProvider));
        day = new Day(relations.friendshipActions, relations.friendshipTable, lunch, output, tableFormatter);
    }

    private int getMaxNameLength(AnimalsProvider animalsProvider) {
        Optional<Integer> length = animalsProvider.getAnimals().stream()
                .map(Animal::getName)
                .map(String::length)
                .max(Integer::compare);

        length.orElseThrow(() -> new IllegalArgumentException("Animals provider doesn't give any animals."));
        return length.get();
    }

    private int getNumberOfParticipants(AnimalsProvider animalsProvider) {
        return animalsProvider.getAnimals().size();
    }
}
