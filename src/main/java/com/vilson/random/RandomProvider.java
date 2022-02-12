package com.vilson.random;

import java.util.List;

public interface RandomProvider<T> {
    int provideRandomIntFromZeroToExcluded(int end);

    void shuffle(List<T> collection);
}
