package com.vilson.relations;

import java.util.List;

class Utils {

    static <T> boolean areListsEqualAsSets(List<T> firstList, List<T> secondList) {
        return firstList.size() == secondList.size() &&
                firstList.containsAll(secondList) &&
                secondList.containsAll(firstList);
    }

    static <T> boolean areListsDisjoint(List<T> firstList, List<T> secondList) {
        return firstList.stream().noneMatch(secondList::contains);
    }
}
