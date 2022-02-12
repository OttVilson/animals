package com.vilson.animals;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DogTest {

    @Test
    void shouldHaveAllFieldsPresentInToStringOutput() {
        Dog dog = new Dog("testDog", "testFood", "testBreed");
        for (Field field : Chicken.class.getFields()) {
            System.out.println(field.getName());
            assertTrue(dog.toString().contains(field.getName() + "asasdasd"));
        }

    }
}
