package com.vilson.relations;

public class HardcodedConfigurationForProbabilisticFriendshipRules
        implements ConfigurationForProbabilisticFriendshipRules {
    @Override
    public int getMaxNumberOfFriendsForGoodProbabilityToGetFriends() {
        return 2;
    }

    @Override
    public int getGoodProbability() {
        return 90;
    }

    @Override
    public int getBadProbability() {
        return 10;
    }
}
