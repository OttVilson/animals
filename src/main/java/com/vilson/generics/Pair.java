package com.vilson.generics;

import java.util.Arrays;
import java.util.List;

public class Pair<T> {

    final T one;
    final T other;

    Pair(T one, T other) {
        validateInput(one, other);
        this.one = one;
        this.other = other;
    }

    public List<T> getComponents() {
        return Arrays.asList(one, other);
    }

    private void validateInput(T one, T other) {
        if (one == null || other == null)
            throw new IllegalArgumentException("Both arguments to a pair must be non-null.");
    }
}
