package com.vilson.friends;

import com.vilson.animals.Animal;

import java.util.List;

interface AnimalRelations {
    void addFriend(Animal animal);

    void addBestFriend(Animal animal);

    void removeFriend(Animal animal);

    int getNumberOfFriends();

    List<Animal> getFriendsOtherThanBestFriend();

    List<Animal> getAllFriends();

    List<Animal> getCurrentNonFriends();
}
