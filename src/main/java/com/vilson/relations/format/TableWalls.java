package com.vilson.relations.format;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class TableWalls {
    private final static List<String> upperBorderWalls = Arrays.asList("╔", "╦", "═", "╗");
    private final static List<String> headerFloorWalls = Arrays.asList("╠", "╬", "═", "╣");
    private final static List<String> contentRowWalls = Arrays.asList("║", "║", " ", "║");
    private final static List<String> contentFloorWalls = Arrays.asList("║", "║", " ", "║");
    private final static List<String> lowerBorderWalls = Arrays.asList("╚", "╩", "═", "╝");

    final static String leftOuterWall = "%1$s";
    final static String firstColumnsRightWall = "%2$s";
    final static String ordinaryInteriorWall = "%3$s";
    final static String rightOuterWall = "%4$s";

    enum Walls {
        UpperBorder(upperBorderWalls),
        HeaderFloor(headerFloorWalls),
        ContentRow(contentRowWalls),
        ContentFloor(contentFloorWalls),
        LowerBorder(lowerBorderWalls);

        private final List<String> walls;

        private Walls(List<String> walls) {
            this.walls = walls;
        }

        Object[] getPreparedElementsWithWalls(List<String> contents) {
            List<String> copyOfContents = new LinkedList<>(contents);
            copyOfContents.addAll(0, walls);
            return copyOfContents.toArray();
        }
    }
}
