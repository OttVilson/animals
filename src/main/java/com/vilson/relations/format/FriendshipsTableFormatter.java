package com.vilson.relations.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vilson.relations.format.TableWalls.Walls;

public class FriendshipsTableFormatter {
    private final int cellWidth;
    private final int padding = 0;
    private final Format format;
    private final List<String> horizontalBorders;

    public FriendshipsTableFormatter(int numberOfPossibleFriends, int maxNameLength) {
        cellWidth = maxNameLength + padding;

        int numberOfColumns = numberOfPossibleFriends + 1;
        format = new Format(numberOfColumns);
        horizontalBorders = initializeHorizontalBorders(numberOfColumns);
    }

    public String getFormattedTable(List<List<String>> friendshipTable) {
        List<String> content = getContentRows(friendshipTable);
        List<String> mixedRows = boundContentRowsWithBorders(content);
        return String.join(System.lineSeparator(), mixedRows);
    }

    private List<String> boundContentRowsWithBorders(List<String> content) {
        List<String> mixed = new ArrayList<>();
        List<List<String>> tuple = Arrays.asList(horizontalBorders, content);
        int summedLength = content.size() + horizontalBorders.size();
        mix(tuple, summedLength, mixed);

        return mixed;
    }

    private void mix(List<List<String>> tuple, int summedLength, List<String> target) {
        for (int i = 0; i < summedLength; i++) {
            int whichArray = i % 2;
            int whichElement = i / 2;
            target.add(tuple.get(whichArray).get(whichElement));
        }
    }

    private List<String> initializeHorizontalBorders(int numberOfColumns) {
        CellHorizontalBorders cellHorizontalBorders = new CellHorizontalBorders(numberOfColumns, cellWidth);
        List<String> doubleCellBorders = cellHorizontalBorders.getDoubleHorizontalBordersForAllContentCells();
        List<String> singleCellBorders = cellHorizontalBorders.getSingleHorizontalBordersForAllContentCells();

        String upperBorder = getRow(doubleCellBorders, Walls.UpperBorder);
        String headerFloorBorder = getRow(doubleCellBorders, Walls.HeaderFloor);
        String interiorHorizontalBorder = getRow(singleCellBorders, Walls.ContentFloor);
        String lowerBorder = getRow(doubleCellBorders, Walls.LowerBorder);

        return addBorders(upperBorder, headerFloorBorder, interiorHorizontalBorder, lowerBorder, numberOfColumns);
    }

    private List<String> addBorders(String upperBorder, String headerFloorBorder, String interiorHorizontalBorder,
                                    String lowerBorder, int numberOfColumns) {
        List<String> borders = new ArrayList<>();

        borders.add(upperBorder);
        borders.add(headerFloorBorder);
        for (int i = 0; i < getNumberOfInteriorBorders(numberOfColumns, 3); i++)
            borders.add(interiorHorizontalBorder);
        borders.add(lowerBorder);
        return borders;
    }

    private int getNumberOfInteriorBorders(int numberOfColumns, int numberOfOtherBorders) {
        int numberOfBorders = numberOfColumns + 1;
        return numberOfBorders - numberOfOtherBorders;
    }

    private List<String> getContentRows(List<List<String>> friendshipTable) {
        int startFromComparisons = 1;
        for (int i = startFromComparisons; i < friendshipTable.size(); i++)
            replaceSelfSelfComparisonWithBackslash(friendshipTable.get(i), i);

        return friendshipTable.stream()
                .map(this::getContentRow)
                .collect(Collectors.toList());
    }

    private void replaceSelfSelfComparisonWithBackslash(List<String> row, int index) {
        row.remove(index);
        row.add(index, "\\");
    }

    private String getContentRow(List<String> row) {
        List<String> elementsPreparedForTable = row.stream().map(this::padContent)
                .collect(Collectors.toList());

        return getRow(elementsPreparedForTable, Walls.ContentRow);
    }

    private String getRow(List<String> row, Walls walls) {
        return String.format(format.getRowFormatWhereFirstColumnIsOutlined(),
                walls.getPreparedElementsWithWalls(row));
    }

    private String padContent(String content) {
        int contentWidth = content.length();
        int leftPadding = (cellWidth - contentWidth) / 2;
        int rightPadding = cellWidth - contentWidth - leftPadding;
        return " ".repeat(leftPadding) + content + " ".repeat(rightPadding);
    }
}
