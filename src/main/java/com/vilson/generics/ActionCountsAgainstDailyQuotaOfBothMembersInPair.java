package com.vilson.generics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActionCountsAgainstDailyQuotaOfBothMembersInPair {

    static public <T> IteratorWithFeedback<T> getIterator(List<T> initialParticipants) {
        return new Iterator<>(initialParticipants);
    }

    private static class Iterator<T> implements IteratorWithFeedback<T> {

        private final List<T> participants;
        private final List<T> stillInGame;
        private int nextIndex = 0;

        private Iterator(List<T> initialParticipants) {
            participants = new LinkedList<>(initialParticipants);
            stillInGame = new ArrayList<>(initialParticipants);
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
            pair.getComponents().forEach(stillInGame::remove);
            pair.getComponents().forEach(this::ifNeededMoveElementLeftOfNextIndexAndUpdateCounter);
        }

        @Override
        public void removeThoseNotParticipatingAnymoreFromList(List<T> listToChooseRandomFrom) {
            listToChooseRandomFrom.retainAll(stillInGame);
        }

        private void ifNeededMoveElementLeftOfNextIndexAndUpdateCounter(T element) {
            int index = participants.indexOf(element);
            if (index >= nextIndex) {
                participants.remove(index);
                participants.add(0, element);
                nextIndex++;
            }
        }
    }
}
