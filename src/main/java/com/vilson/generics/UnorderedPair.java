package com.vilson.generics;

import java.util.Arrays;
import java.util.List;

public class UnorderedPair<T> {

    private final T one;
    private final T other;

    public UnorderedPair(T one, T other) {
        this.one = one;
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UnorderedPair<?>)) return false;

        UnorderedPair<?> otherPair = (UnorderedPair<?>) o;
        return symmetrizedEqualityCheck(otherPair);
    }

    @Override
    public int hashCode() {
        return symmetrizedHashCode();
    }

    public List<T> getComponents() {
        return Arrays.asList(one, other);
    }

    private boolean symmetrizedEqualityCheck(UnorderedPair<?> otherPair) {
        return isSamePositionMatch(otherPair) || isMixedPositionMatch(otherPair);
    }

    private boolean isSamePositionMatch(UnorderedPair<?> otherPair) {
        return one.equals(otherPair.one) && other.equals(otherPair.other);
    }

    private boolean isMixedPositionMatch(UnorderedPair<?> otherPair) {
        return one.equals(otherPair.other) && other.equals(otherPair.one);
    }

    private int symmetrizedHashCode() {
        return one.hashCode() + other.hashCode();
    }
}
