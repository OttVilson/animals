package com.vilson.table;

import static com.vilson.table.TableWalls.*;

class FormatForm {
    private final String contentRowFormatWhereFirstColumnIsOutlined;
    private final String horizontalBorderRowFormatWhereFirstColumnIsOutlined;

    FormatForm(int numberOfColumns) {
        this.contentRowFormatWhereFirstColumnIsOutlined =
                initializeRowFormatWhereFirstColumnIsOutlined(new MovingFormatMarker(), numberOfColumns);
        this.horizontalBorderRowFormatWhereFirstColumnIsOutlined =
                initializeRowFormatWhereFirstColumnIsOutlined(new StaticFormatMarker(), numberOfColumns);
    }

    String getContentRowFormatWhereFirstColumnIsOutlined() {
        return this.contentRowFormatWhereFirstColumnIsOutlined;
    }

    String getHorizontalBorderRowFormatWhereFirstColumnIsOutlined() {
        return this.horizontalBorderRowFormatWhereFirstColumnIsOutlined;
    }

    private String initializeRowFormatWhereFirstColumnIsOutlined(FormatMarker fm, int numberOfColumns) {
        String cellMarker = fm.next();
        String interior = getInterior(fm, numberOfColumns);
        return leftOuterWall + cellMarker + firstColumnsRightWall + interior + rightOuterWall;
    }

    private String getInterior(FormatMarker fm, int numberOfColumns) {
        int numberOfOrdinaryInteriorWalls = numberOfColumns - 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfOrdinaryInteriorWalls; i++)
            sb.append(fm.next()).append(ordinaryInteriorWall);

        return sb.append(fm.next()).toString();
    }
}
