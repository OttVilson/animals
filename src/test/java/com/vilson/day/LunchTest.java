package com.vilson.day;

import com.vilson.animals.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LunchTest {

    private FakeAnimalsProvider animalsProvider;

    @BeforeEach
    private void init() {
        animalsProvider = new FakeAnimalsProvider();
    }

    @Test
    void lunchOutputTest() {
        Animal first = new Animal("firstName", "firstFood");
        Animal second = new Animal("secondName", "secondFood");
        Animal third = new Animal("thirdName", "firstFood");

        animalsProvider.provideAnimals(Arrays.asList(first, second, third));
        Lunch lunch = new Lunch(animalsProvider);

        String output = lunch.getEatersGroupedByFood();

        assertEquals(output.lines().count(), 2);
    }

}
