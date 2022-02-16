package com.vilson.relations;

import com.vilson.animals.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.vilson.relations.Utils.areListsDisjoint;
import static com.vilson.relations.Utils.areListsEqualAsSets;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalRelationsTest {

    Animal first;
    Animal second;
    Animal third;
    Animal fourth;

    List<Animal> listOfThree;
    AnimalRelations animalRelations;

    @BeforeEach
    void init() {
        first = new Animal("firstName", "food");
        second = new Animal("secondName", "food");
        third = new Animal("thirdName", "food");
        fourth = new Animal("fourthName", "food");

        listOfThree = new ArrayList<>();
        listOfThree.addAll(Arrays.asList(first, second, third));
        animalRelations = new AnimalRelationsImpl(listOfThree);
    }

    @Test
    void shouldStartWithAllProvidedAnimalsAsNonFriend() {
        List<Animal> nonFriends = animalRelations.getCurrentNonFriends();

        assertTrue(areListsEqualAsSets(listOfThree, nonFriends));
    }

    @Test
    void shouldMakeDefensiveCopyOfList() {
        listOfThree.add(fourth);
        assertFalse(animalRelations.getCurrentNonFriends().contains(fourth));

        listOfThree.remove(second);
        assertTrue(animalRelations.getCurrentNonFriends().contains(second));
    }

    @Test
    void shouldStartWithNumberOfFriendsBeingZero() {
        assertEquals(0, animalRelations.getNumberOfFriends());
    }

    @Test
    void shouldThrowIfAnimalNotProvidedAtInitializationIsPushedAsFriend() {
        assertThrows(IllegalArgumentException.class, () -> animalRelations.addFriend(fourth));
    }

    @Test
    void shouldThrowIfAnimalNotProvidedAtInitializationIsPushedAsBestFriend() {
        assertThrows(IllegalArgumentException.class, () -> animalRelations.addBestFriend(fourth));
    }

    @Test
    void shouldThrowIfAnimalNotProvidedAtInitializationIsBeingRemovedFromListOfFriends() {
        assertThrows(IllegalArgumentException.class, () -> animalRelations.removeFriend(fourth));
    }

    @Test
    void shouldReturnAnimalInFriendsListIfSetAsFriend() {
        assertTrue(areListsEqualAsSets(animalRelations.getCurrentNonFriends(), listOfThree));
        assertTrue(areListsEqualAsSets(animalRelations.getFriendsOtherThanBestFriend(), Collections.emptyList()));
        assertEquals(0, animalRelations.getNumberOfFriends());

        animalRelations.addFriend(second);

        assertFalse(animalRelations.getCurrentNonFriends().contains(second));
        assertTrue(animalRelations.getFriendsOtherThanBestFriend().contains(second));
        assertEquals(1, animalRelations.getNumberOfFriends());
        assertTrue(areListsDisjoint(
                animalRelations.getFriendsOtherThanBestFriend(),
                animalRelations.getCurrentNonFriends()));
    }

    @Test
    void shouldThrowIfAlreadyFriendIsPushedAsFriend() {
        animalRelations.addFriend(second);

        assertThrows(IllegalArgumentException.class, () -> animalRelations.addFriend(second));
    }

    @Test
    void shouldThrowIfAlreadyFriendIsPushedAsBestFriend() {
        animalRelations.addFriend(second);

        assertThrows(IllegalArgumentException.class, () -> animalRelations.addBestFriend(second));
    }

    @Test
    void shouldIndicateAddBestFriendByRemovingFromNonFriendsAndIncreasingNumberOfFriends() {
        animalRelations.addBestFriend(second);

        assertEquals(1, animalRelations.getNumberOfFriends());
        assertFalse(animalRelations.getCurrentNonFriends().contains(second));
        assertFalse(animalRelations.getFriendsOtherThanBestFriend().contains(second));
    }

    @Test
    void shouldThrowWhenPushingAsFriendAnAnimalWhoIsAlreadyBestFriend() {
        animalRelations.addBestFriend(second);

        assertThrows(IllegalArgumentException.class, () -> animalRelations.addFriend(second));
    }

    @Test
    void shouldRemoveAnOrdinaryFriend() {
        animalRelations.addFriend(second);
        assertTrue(animalRelations.getFriendsOtherThanBestFriend().contains(second));

        animalRelations.removeFriend(second);
        assertEquals(0, animalRelations.getNumberOfFriends());
        assertTrue(animalRelations.getCurrentNonFriends().contains(second));
        assertTrue(areListsDisjoint(animalRelations.getCurrentNonFriends(),
                animalRelations.getFriendsOtherThanBestFriend()));
    }

    @Test
    void shouldThrowIfTryingToRemoveFriendshipFromNonFriend() {
        assertThrows(IllegalArgumentException.class, () -> animalRelations.removeFriend(second));
    }

    @Test
    void shouldThrowIfTryingToRemoveBestFriend() {
        animalRelations.addBestFriend(second);
        assertThrows(IllegalArgumentException.class, () -> animalRelations.removeFriend(second));
    }

    @Test
    void shouldAllowAddingAllOtherAnimalsAsFriends() {
        animalRelations.addFriend(first);
        animalRelations.addFriend(third);
        animalRelations.addFriend(second);

        assertTrue(areListsEqualAsSets(animalRelations.getFriendsOtherThanBestFriend(), listOfThree));
        assertEquals(3, animalRelations.getNumberOfFriends());
    }

    @Test
    void shouldAllowRemovingAllOrdinaryFriends() {
        shouldAllowAddingAllOtherAnimalsAsFriends();

        animalRelations.removeFriend(first);
        animalRelations.removeFriend(second);
        animalRelations.removeFriend(third);

        assertEquals(0, animalRelations.getNumberOfFriends());
        assertTrue(areListsEqualAsSets(animalRelations.getFriendsOtherThanBestFriend(), Collections.emptyList()));
        assertTrue(areListsEqualAsSets(animalRelations.getCurrentNonFriends(), listOfThree));
    }
}
