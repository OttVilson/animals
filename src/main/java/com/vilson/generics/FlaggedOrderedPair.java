package com.vilson.generics;

public class FlaggedOrderedPair<T> extends Pair<T> {

    private final boolean flag;

    public FlaggedOrderedPair(T first, T second, boolean flag) {
        super(first, second);
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }
}
