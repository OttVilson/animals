package com.vilson.generics;

import java.util.Arrays;
import java.util.List;

public class FlaggedOrderedPair<T> {

    private final boolean flag;
    private final T first;
    private final T second;

    public FlaggedOrderedPair(T first, T second, boolean flag) {
        this.first = first;
        this.second = second;
        this.flag = flag;
    }

    public List<T> getPairComponents() {
        return Arrays.asList(first, second);
    }

    public boolean getFlag() {
        return flag;
    }
}
