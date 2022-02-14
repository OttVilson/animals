package com.vilson.relations.format;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class CellHorizontalBorders {

    private final List<String> doubleHorizontalBordersForAllContentCells;
    private final List<String> singleHorizontalBordersForAllContentCells;

    CellHorizontalBorders(int numberOfColumns, int cellWith) {
        doubleHorizontalBordersForAllContentCells = initializeBorders('═', cellWith, numberOfColumns);
        singleHorizontalBordersForAllContentCells = initializeBorders('─', cellWith, numberOfColumns);
    }

    List<String> getDoubleHorizontalBordersForAllContentCells() {
        return doubleHorizontalBordersForAllContentCells;
    }

    List<String> getSingleHorizontalBordersForAllContentCells() {
        return singleHorizontalBordersForAllContentCells;
    }

    private List<String> initializeBorders(char borderType, int cellWith, int numberOfColumns) {
        String cellBorder = getBorder(borderType, cellWith);

        String[] exampleColumns = new String[numberOfColumns];
        return Arrays.stream(exampleColumns)
                .map(everythingTo(cellBorder)).collect(Collectors.toList());
    }

    private String getBorder(char borderType, int cellWith) {
        return String.valueOf(borderType).repeat(cellWith);
    }

    private Function<String, String> everythingTo(String relevant) {
        return (String irrelevant) -> relevant;
    }
}
