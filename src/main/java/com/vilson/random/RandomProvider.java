package com.vilson.random;

import java.util.List;

public interface RandomProvider {
    int provideRandomIntFromZeroToExcluded(int end);

    void shuffle(List<?> collection);
}
