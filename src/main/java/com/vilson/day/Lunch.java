package com.vilson.day;

import com.vilson.animals.Animal;
import com.vilson.animals.AnimalsProvider;
import com.vilson.output.Output;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lunch {

    private final String eatersGroupedByFood;
    private final Output output;

    public Lunch(AnimalsProvider animalsProvider, Output output) {
        eatersGroupedByFood = initializeEatersGroupedByFood(animalsProvider);
        this.output = output;
    }

    public void outputEatersGroupedByFood() {
        output.forwardMessage(eatersGroupedByFood);
    }

    private String initializeEatersGroupedByFood(AnimalsProvider animalsProvider) {
        Map<String, Set<String>> namesOfAnimalsGroupedByFavoriteFood =
                getNamesOfAnimalsGroupedByFavoriteFood(animalsProvider);

        return composeSentences(namesOfAnimalsGroupedByFavoriteFood);
    }

    private Map<String, Set<String>> getNamesOfAnimalsGroupedByFavoriteFood(AnimalsProvider animalsProvider) {
        Collector<Animal, ?, Set<String>> reduceStreamOfAnimalsToSetOfNames =
                Collectors.mapping(Animal::getName, Collectors.toSet());
        Collector<Animal, ?, Map<String, Set<String>>> groupAnimalsByFavoriteFoodAndReduceGroupsToSetsOfNames =
                Collectors.groupingBy(Animal::getFavoriteFood, reduceStreamOfAnimalsToSetOfNames);

        Stream<Animal> animalStream = animalsProvider.getAnimals().stream();
        return animalStream.collect(groupAnimalsByFavoriteFoodAndReduceGroupsToSetsOfNames);
    }

    private String composeSentences(Map<String, Set<String>> namesOfAnimalsGroupedByFavoriteFood) {
        Collector<CharSequence, ?, String> joinSentencesByAddingSystemLineSeparatorInbetween =
                Collectors.joining(System.lineSeparator());

        return namesOfAnimalsGroupedByFavoriteFood.entrySet().stream()
                .map(this::getSentenceForFood)
                .collect(joinSentencesByAddingSystemLineSeparatorInbetween);
    }

    private String getSentenceForFood(Map.Entry<String, Set<String>> foodAndItsEaters) {
        String food = foodAndItsEaters.getKey();
        Set<String> eaters = foodAndItsEaters.getValue();

        String format = "%s %s eating %s.";
        return String.format(format, joinNames(eaters), getVerb(eaters), food);
    }

    private String joinNames(Set<String> names) {
        if (names.isEmpty()) return "No one";

        return String.join(" and ", names);
    }

    private String getVerb(Set<String> names) {
        if (names.size() < 2) return "is";
        else return "are";
    }
}
