package com.vilson.day;

import com.vilson.FakeAnimalsProvider;
import com.vilson.animals.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LunchTest {

    private FakeAnimalsProvider animalsProvider;
    private List<String> outputLines;

    @BeforeEach
    void init() {
        animalsProvider = new FakeAnimalsProvider();
        Animal first = new Animal("firstName", "firstFood");
        Animal second = new Animal("secondName", "secondFood");
        Animal third = new Animal("thirdName", "firstFood");
        animalsProvider.provideAnimals(Arrays.asList(first, second, third));

        Lunch lunch = new Lunch(animalsProvider);
        outputLines = lunch.getEatersGroupedByFood().lines().collect(Collectors.toList());
    }

    @Test
    void lunchOutputNoOfLinesEqualsNumberOfDistinctFoodsTest() {
        assertEquals(outputLines.size(), numberOfUniqueFavoriteFoods(animalsProvider.getAnimals()));
    }

    @Test
    void lunchOutputLineForFoodContainsItsEatersTest() {
        assertLineForFoodContainsEaters("firstFood", "firstName", "thirdName");
        assertLineForFoodContainsEaters("secondFood", "secondName");
    }

    private long numberOfUniqueFavoriteFoods(List<Animal> animals) {
        return animals.stream()
                .map(Animal::getFavoriteFood)
                .distinct()
                .count();
    }

    private String uniqueLineFor(String food) {
        List<String> singleLineList = outputLines.stream()
                .filter(line -> line.contains(food))
                .collect(Collectors.toList());
        assertListSizeEqualsOne(singleLineList);
        return singleLineList.get(0);
    }

    private void assertLineForFoodContainsEaters(String food, String... eaters) {
        String lineForFood = uniqueLineFor(food);
        for (String name : eaters)
            assertTrue(lineForFood.contains(name),
                    String.format("The name %s is not present in the sentence (%s) for the food %s.",
                            name, lineForFood, food));
    }

    private void assertListSizeEqualsOne(List<String> singleElementList) {
        int numberOfElements = singleElementList.size();
        if (numberOfElements != 1)
            fail("The number of items in stream is not one.");
    }
}
