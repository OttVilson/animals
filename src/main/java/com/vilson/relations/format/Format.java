package com.vilson.relations.format;

import static com.vilson.relations.format.TableWalls.*;

class Format {
    private final int numberOfColumns;
    private final String rowFormatWhereFirstColumnIsOutlined;

    Format(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        this.rowFormatWhereFirstColumnIsOutlined = initializeRowFormatWhereFirstColumnIsOutlined();
    }

    String getRowFormatWhereFirstColumnIsOutlined() {
        return this.rowFormatWhereFirstColumnIsOutlined;
    }

    private String initializeRowFormatWhereFirstColumnIsOutlined() {
        int numberOfDistinctWalls = 4;
        FormatMarker fm = new FormatMarker(numberOfDistinctWalls + 1);

        String cellMarker = fm.next();
        String interior = getInterior(fm);
        return leftOuterWall + cellMarker + firstColumnsRightWall + interior + rightOuterWall;
    }

    private String getInterior(FormatMarker fm) {
        int numberOfOrdinaryInteriorWalls = numberOfColumns - 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfOrdinaryInteriorWalls; i++) {
            sb.append(fm.next()).append(ordinaryInteriorWall);
        }

        return sb.append(fm.next()).toString();
    }

    private static class FormatMarker {
        private int currentIndex;

        FormatMarker(int startFrom) {
            this.currentIndex = startFrom;
        }

        String next() {
            return "%" + currentIndex++ + "$s";
        }
    }
}
