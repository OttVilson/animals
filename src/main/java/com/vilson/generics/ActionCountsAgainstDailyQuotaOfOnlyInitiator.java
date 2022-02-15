package com.vilson.generics;

import java.util.ArrayList;
import java.util.List;

public class ActionCountsAgainstDailyQuotaOfOnlyInitiator {

    static public <T> IteratorWithFeedback<T> getIterator(List<T> initialParticipants) {
        return new Iterator<>(initialParticipants);
    }

    private static class Iterator<T> implements IteratorWithFeedback<T> {
        List<T> participants;
        int nextIndex = 0;

        private Iterator(List<T> initialParticipants) {
            this.participants = new ArrayList<>(initialParticipants);
        }

        @Override
        public boolean hasNext() {
            return nextIndex < participants.size();
        }

        @Override
        public T next() {
            return participants.get(nextIndex++);
        }

        @Override
        public void feedback(Pair<T> pair) {
        }

        @Override
        public void removeThoseNotParticipatingAnymoreFromList(List<T> listToChooseRandomFrom) {
        }
    }
}
