package com.vilson.random;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomProviderImpl implements RandomProvider {

    @Override
    public int provideRandomIntFromZeroToExcluded(int end) {
        return ThreadLocalRandom.current().nextInt(end);
    }

    @Override
    public void shuffle(List<?> collection) {
        Collections.shuffle(collection);
    }
}
