package com.vilson.generics;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class UnorderedPair<T> extends Pair<T> {

    public UnorderedPair(T one, T other) {
        super(one, other);
    }

    public static <T> Function<T, UnorderedPair<T>> formUnorderedPairWith(T other) {
        return (T one) -> new UnorderedPair<>(one, other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

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

    private int symmetrizedHashCode() {
        return one.hashCode() + other.hashCode();
    }

    private boolean isSamePositionMatch(UnorderedPair<?> otherPair) {
        return one.equals(otherPair.one) && other.equals(otherPair.other);
    }

    private boolean isMixedPositionMatch(UnorderedPair<?> otherPair) {
        return one.equals(otherPair.other) && other.equals(otherPair.one);
    }
}
