package com.vilson.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.vilson.table.TableWalls.Wall.*;

class HorizontalBordersProvider {

    private final String format;
    private final int cellWidth;
    private final int numberOfColumns;
    private final List<String> horizontalBorders;

    HorizontalBordersProvider(FormatForm formatForm, int cellWidth, int numberOfColumns) {
        format = formatForm.getHorizontalBorderRowFormatWhereFirstColumnIsOutlined();
        this.cellWidth = cellWidth;
        this.numberOfColumns = numberOfColumns;
        horizontalBorders = new ArrayList<>();
        fillBorders();
    }

    public List<String> getHorizontalBorders() {
        return horizontalBorders;
    }

    private void fillBorders() {
        List<String> doubleHorizontalCellBorder = getCellBorder('═', cellWidth);

        horizontalBorders.add(getBorder(UPPER_BORDER, doubleHorizontalCellBorder));
        horizontalBorders.add(getBorder(HEADER_FLOOR, doubleHorizontalCellBorder));
        addInteriorBorders();
        horizontalBorders.add(getBorder(LOWER_BORDER, doubleHorizontalCellBorder));
    }

    private List<String> getCellBorder(char borderType, int cellWith) {
        String border = String.valueOf(borderType).repeat(cellWith);
        return Collections.singletonList(border);
    }

    private String getBorder(TableWalls.Wall wall, List<String> cellBorderForm) {
        return String.format(format, wall.getElementsAppendedToWalls(cellBorderForm));
    }

    private void addInteriorBorders() {
        List<String> singleHorizontalCellBorder = getCellBorder('─', cellWidth);

        String interiorBorder = getBorder(CONTENT_FLOOR, singleHorizontalCellBorder);
        for (int i = 0; i < getNumberOfInteriorBorders(3); i++)
            horizontalBorders.add(interiorBorder);
    }

    private int getNumberOfInteriorBorders(int otherBorders) {
        int numberOfBorders = numberOfColumns + 1;
        return numberOfBorders - otherBorders;
    }
}
