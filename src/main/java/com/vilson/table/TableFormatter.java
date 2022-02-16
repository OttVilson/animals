package com.vilson.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vilson.table.TableWalls.Wall.CONTENT_ROW;

public class TableFormatter {
    private final int cellWidth;
    private final int padding = 0;
    private final int numberOfColumns;
    private final FormatForm formatForm;
    private final List<String> horizontalBorders;

    public TableFormatter(int maxNameLength, int numberOfParticipants) {
        validateNumberOfParticipants(numberOfParticipants);
        cellWidth = maxNameLength + padding;
        numberOfColumns = numberOfParticipants + 1;
        formatForm = new FormatForm(numberOfColumns);
        horizontalBorders = initializedHorizontalBorders(numberOfColumns);
    }

    public String getFormattedTable(List<List<String>> tableOfPairs) {
        validateInputTable(tableOfPairs);
        List<String> content = getContentRows(tableOfPairs);
        List<String> mixedRows = bindContentRowsWithBorders(content);
        return String.join(System.lineSeparator(), mixedRows);
    }

    private void validateNumberOfParticipants(int numberOfParticipants) {
        if (numberOfParticipants < 2)
            throw new IllegalArgumentException("This table format is not usable for less than two participants");
    }

    private List<String> initializedHorizontalBorders(int numberOfColumns) {
        HorizontalBordersProvider horizontalBordersProvider =
                new HorizontalBordersProvider(formatForm, cellWidth, numberOfColumns);
        return horizontalBordersProvider.getHorizontalBorders();
    }

    private void validateInputTable(List<List<String>> table) {
        validateThatTableHasCorrectNumberOfColumnsAndRows(table);
    }

    private List<String> getContentRows(List<List<String>> friendshipTable) {
        int startFromComparisons = 1;
        for (int i = startFromComparisons; i < friendshipTable.size(); i++)
            replaceSelfSelfComparisonWithBackslash(friendshipTable.get(i), i);

        return friendshipTable.stream()
                .map(this::getContentRow)
                .collect(Collectors.toList());
    }

    private List<String> bindContentRowsWithBorders(List<String> content) {
        List<String> mixed = new ArrayList<>();
        List<List<String>> tuple = Arrays.asList(horizontalBorders, content);
        int summedLength = content.size() + horizontalBorders.size();
        mix(tuple, summedLength, mixed);

        return mixed;
    }

    private void validateThatTableHasCorrectNumberOfColumnsAndRows(List<List<String>> table) {
        validateThatTableIsSquare(table);
        boolean correctSideLength = numberOfColumns == table.size();
        if (!correctSideLength)
            throw new IllegalArgumentException("The provided table is of different size than suitable for this " +
                    "instance of " + this.getClass());
    }

    private void replaceSelfSelfComparisonWithBackslash(List<String> row, int index) {
        row.remove(index);
        row.add(index, "\\");
    }

    private String getContentRow(List<String> row) {
        List<String> elementsPreparedForTable = row.stream().map(this::padContent)
                .collect(Collectors.toList());

        return getRow(elementsPreparedForTable);
    }

    private void mix(List<List<String>> tuple, int summedLength, List<String> target) {
        for (int i = 0; i < summedLength; i++) {
            int whichArray = i % 2;
            int whichElement = i / 2;
            target.add(tuple.get(whichArray).get(whichElement));
        }
    }

    private void validateThatTableIsSquare(List<List<String>> table) {
        int numberOfRows = table.size();
        Optional<List<String>> incorrectRow = table.stream()
                .filter(row -> row.size() != numberOfRows).findAny();

        if (incorrectRow.isPresent())
            throw new IllegalArgumentException("The provided table is not square");
    }

    private String padContent(String content) {
        int contentWidth = content.length();
        int leftPadding = (cellWidth - contentWidth) / 2;
        int rightPadding = cellWidth - contentWidth - leftPadding;
        return " ".repeat(leftPadding) + content + " ".repeat(rightPadding);
    }

    private String getRow(List<String> row) {
        return String.format(formatForm.getContentRowFormatWhereFirstColumnIsOutlined(),
                CONTENT_ROW.getElementsAppendedToWalls(row));
    }
}
