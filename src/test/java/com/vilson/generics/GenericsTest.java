package com.vilson.generics;

import com.vilson.animals.Animal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericsTest {

    @Nested
    class UnorderedPairTest {

        private Animal one;
        private Animal other;

        @BeforeEach
        void init() {
            one = new Animal("oneAnimal", "food");
            other = new Animal("otherAnimal", "food");
        }

        @Test
        void throwsIfEitherOfTheConstructorArgumentsIsNull() {
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new UnorderedPair<>(one, null),
                    "Unordered pair constructor should throw exception on null argument");

            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> new UnorderedPair<>(null, other),
                    "Unordered pair constructor should throw exception on null argument");
        }

        @Test
        void equalsWhenOrderOfConstructorArgumentsAreSwapped() {
            UnorderedPair<Animal> firstOrder = new UnorderedPair<>(one, other);
            UnorderedPair<Animal> secondOrder = new UnorderedPair<>(other, one);

            assertEquals(firstOrder, secondOrder, "The pairs should be equal even if constructor order is changed");
        }

        @Test
        void hashCodeEqualsWhenOrderOfConstructorArgumentsAreSwapped() {
            UnorderedPair<Animal> firstOrder = new UnorderedPair<>(one, other);
            UnorderedPair<Animal> secondOrder = new UnorderedPair<>(other, one);

            assertEquals(firstOrder.hashCode(), secondOrder.hashCode(),
                    "The hashCode should be the same even if constructor arguments are changed.");
        }

        @Test
        void canBeUsedAsValueInHashMap() {
            Map<UnorderedPair<Animal>, String> map = new HashMap<>();
            UnorderedPair<Animal> pairForPut = new UnorderedPair<>(one, other);
            UnorderedPair<Animal> pairForGet = new UnorderedPair<>(other, one);

            map.put(pairForPut, "Value");

            assertEquals("Value", map.get(pairForGet),
                    "Should be able to get value from hashMap even if different instance of UnorderedPair with same " +
                            "(unordered) constructor parameters is used.");
        }
    }

    @Nested
    class IteratorWithFeedbackTestActionCountsAgainstQuotaOfBoth {
        private Animal first;
        private Animal second;
        private Animal third;
        private List<Animal> listOfThree;
        private IteratorWithFeedback<Animal> iterator;

        @BeforeEach
        void init() {
            first = new Animal("firstName", "food");
            second = new Animal("secondName", "food");
            third = new Animal("thirdName", "food");
            listOfThree = Arrays.asList(first, second, third);

            iterator = ActionCountsAgainstDailyQuotaOfBothMembersInPair.getIterator(listOfThree);
        }

        @Test
        void shouldRemovePairIfProvidedInFeedbackTest() {
            iterator.feedback(new UnorderedPair<>(first, second));

            List<Animal> remainingElements = new ArrayList<>();
            while (iterator.hasNext())
                remainingElements.add(iterator.next());

            assertEquals(1, remainingElements.size());
            assertTrue(remainingElements.contains(third));
        }

        @Test
        void removeThoseNotParticipatingAnymoreFromTheListShouldWorkTest() {
            iterator.feedback(new UnorderedPair<>(first, second));

            List<Animal> activeParticipants = new ArrayList<>(listOfThree);
            iterator.removeThoseNotParticipatingAnymoreFromList(activeParticipants);

            assertEquals(1, activeParticipants.size());
            assertTrue(activeParticipants.contains(third));
        }
    }


}
