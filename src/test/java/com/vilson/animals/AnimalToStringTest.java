package com.vilson.animals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vilson.animals.Bird.WINGSPAN_FORMAT;
import static com.vilson.animals.Utils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnimalToStringTest {
    private Map<String, String> data;

    @BeforeEach
    void initData() {
        data = new HashMap<>();
        data.put(name, "testName");
        data.put(favoriteFood, "testFood");
    }

    @Test
    void dog() {
        data.put(breed, "testBreed");

        Dog dog = new Dog(get(name), get(favoriteFood), get(breed));

        verifySubtypeOfAnimal(dog);
    }

    @Test
    void cat() {
        Cat cat = new Cat(get(name), get(favoriteFood));

        verifySubtypeOfAnimal(cat);
    }

    @Test
    void chickenWhoLaysEggs() {
        double wingspan = 0.53;
        boolean lays = true;
        data.put(wingspreadInMeters, String.format(WINGSPAN_FORMAT, wingspan));
        data.put(laysEggs, Flagged.doesOrDoesnt(lays) + " ");

        Chicken chicken = new Chicken(get(name), get(favoriteFood), wingspan, lays);

        verifySubtypeOfAnimal(chicken);
    }

    @Test
    void chickenWhoDoesntLayEggs() {
        double wingspan = 0.53;
        boolean lays = false;
        data.put(wingspreadInMeters, String.format(WINGSPAN_FORMAT, wingspan));
        data.put(laysEggs, Flagged.doesOrDoesnt(lays) + " ");

        Chicken chicken = new Chicken(get(name), get(favoriteFood), wingspan, lays);

        verifySubtypeOfAnimal(chicken);
    }

    @Test
    void rooster() {
        double wingspan = 0.72;
        data.put(wingspreadInMeters, String.format(WINGSPAN_FORMAT, wingspan));

        Rooster rooster = new Rooster(get(name), get(favoriteFood), wingspan);

        verifySubtypeOfAnimal(rooster);
    }

    @Test
    void parrotWhoCanSpeak() {
        double wingspan = 0.23;
        boolean canSpeak = true;
        data.put(wingspreadInMeters, String.format(WINGSPAN_FORMAT, wingspan));
        data.put(Utils.canSpeak, Flagged.doesOrDoesnt(canSpeak) + " ");

        Parrot parrot = new Parrot(get(name), get(favoriteFood), wingspan, canSpeak);

        verifySubtypeOfAnimal(parrot);
    }

    @Test
    void parrotWhoCantSpeak() {
        double wingspan = 0.13;
        boolean canSpeak = false;
        data.put(wingspreadInMeters, String.format(WINGSPAN_FORMAT, wingspan));
        data.put(Utils.canSpeak, Flagged.doesOrDoesnt(canSpeak) + " ");

        Parrot parrot = new Parrot(get(name), get(favoriteFood), wingspan, canSpeak);

        verifySubtypeOfAnimal(parrot);
    }

    private void verifySubtypeOfAnimal(Animal animal) {
        String missingField = "The field value %s wasn't contained in the toString output %s.";
        List<String> fieldsToSeeInToStringOutput = new ArrayList<>(data.values());
        String animalToString = animal.toString();

        for (String fieldValue : fieldsToSeeInToStringOutput)
            assertTrue(animalToString.contains(fieldValue), String.format(missingField, fieldValue, animalToString));

        assertAllClassAndSuperclassesInstanceFieldsAreChecked(animal.getClass(), fieldsToSeeInToStringOutput);
    }


    private void assertAllClassAndSuperclassesInstanceFieldsAreChecked(Class clazz,
                                                                       List<String> fieldsToSeeInToStringOutput) {
        String numberOfFieldsMismatch = "The number of fields checked is %d and this doesn't match with the"
                + " number of class' fields %d. %s vs %s";

        int numberOfFieldsChecked = fieldsToSeeInToStringOutput.size();
        int numberOfFieldsOfClassAndSuperclasses = getAllInstanceFieldsFromTheSamePackage(clazz).size();
        assertEquals(numberOfFieldsOfClassAndSuperclasses, numberOfFieldsChecked,
                String.format(numberOfFieldsMismatch, numberOfFieldsChecked, numberOfFieldsOfClassAndSuperclasses,
                        fieldsToSeeInToStringOutput, getAllInstanceFieldsFromTheSamePackage(clazz)));
    }

    private String get(String key) {
        return data.get(key);
    }
}
