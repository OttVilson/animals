package com.vilson.table;

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

    enum Wall {
        UPPER_BORDER(upperBorderWalls),
        HEADER_FLOOR(headerFloorWalls),
        CONTENT_ROW(contentRowWalls),
        CONTENT_FLOOR(contentFloorWalls),
        LOWER_BORDER(lowerBorderWalls);

        private final List<String> walls;

        Wall(List<String> walls) {
            this.walls = walls;
        }

        Object[] getElementsAppendedToWalls(List<String> contents) {
            List<String> copyOfContents = new LinkedList<>(contents);
            copyOfContents.addAll(0, walls);
            return copyOfContents.toArray();
        }
    }

    static abstract class FormatMarker {
        int nextIndex = upperBorderWalls.size() + 1;

        String next() {
            String formatMarker = "%" + nextIndex + "$s";
            updateNextIndex();
            return formatMarker;
        }

        abstract void updateNextIndex();
    }

    static class StaticFormatMarker extends FormatMarker {
        @Override
        void updateNextIndex() {
        }
    }

    static class MovingFormatMarker extends FormatMarker {
        @Override
        void updateNextIndex() {
            nextIndex++;
        }
    }
}
