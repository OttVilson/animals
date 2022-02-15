package com.vilson.day;

import com.vilson.animals.Animal;
import com.vilson.generics.FlaggedOrderedPair;
import com.vilson.generics.UnorderedPair;
import com.vilson.output.Output;
import com.vilson.relations.FriendshipActions;
import com.vilson.relations.FriendshipTable;
import com.vilson.table.TableFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class Day {

    private final FriendshipActions friendshipActions;
    private final FriendshipTable friendshipTable;
    private final Lunch lunch;
    private final Output output;
    private final TableFormatter tableFormatter;

    private static final String POSITIVE_RESPONSE =
            "%1$s is asking %2$s to be friends. %2$s and %1$s are friends now.";
    private static final String NEGATIVE_RESPONSE =
            "%1$s is asking %2$s to be friends. %2$s doesn't want to be friends with %1$s.";

    public Day(FriendshipActions friendshipActions, FriendshipTable friendshipTable, Lunch lunch,
               Output output, TableFormatter tableFormatter) {
        this.friendshipActions = friendshipActions;
        this.friendshipTable = friendshipTable;
        this.lunch = lunch;
        this.output = output;
        this.tableFormatter = tableFormatter;
    }

    public void processDayN(int n) {
        output.forwardMessage("Day-" + n);

        processDailyActivities();

        dailyReport();
    }

    private void processDailyActivities() {
        processBeforeLunch();

        output.forwardMessage("Lunch:");
        output.forwardIndentedMessage(lunch.getEatersGroupedByFood());

        processAfterLunch();
    }

    private void dailyReport() {
        List<List<String>> table = friendshipTable.generateSquareTableWithAtLeastOneCell();
        String formattedTable = tableFormatter.getFormattedTable(table);
        output.forwardMessage(formattedTable);
    }

    private void processBeforeLunch() {
        output.forwardMessage("The broken friendships are:");
        List<UnorderedPair<Animal>> brokenFriendships = friendshipActions.roundOfLosingFriends();
        for (UnorderedPair<Animal> brokenFriendship : brokenFriendships)
            announceBrokenFriendship(brokenFriendship);
    }

    private void processAfterLunch() {
        output.forwardMessage("The friendship start attempts are:");
        List<FlaggedOrderedPair<Animal>> friendshipAttempts =
                friendshipActions.roundOfGainingFriends();
        for (FlaggedOrderedPair<Animal> friendshipAttempt : friendshipAttempts)
            announceFriendshipAttempt(friendshipAttempt);
    }

    private void announceBrokenFriendship(UnorderedPair<Animal> brokenFriendship) {
        String formerFriends = brokenFriendship.getComponents().stream()
                .map(Animal::getName)
                .collect(Collectors.joining(" and "));

        output.forwardIndentedMessage(formerFriends + " are no longer friends.");
    }

    private void announceFriendshipAttempt(FlaggedOrderedPair<Animal> friendshipAttempt) {
        List<Animal> animals = friendshipAttempt.getPairComponents();
        String asker = animals.get(0).getName();
        String responder = animals.get(1).getName();
        String format = friendshipAttempt.getFlag() ? POSITIVE_RESPONSE : NEGATIVE_RESPONSE;
        output.forwardIndentedMessage(String.format(format, asker, responder));
    }
}
