package com.vilson.generics;

import java.util.List;

public interface IteratorWithFeedback<T> {

    boolean hasNext();

    T next();

    void feedback(Pair<T> pair);

    void removeThoseNotParticipatingAnymoreFromList(List<T> listToChooseRandomFrom);
}
