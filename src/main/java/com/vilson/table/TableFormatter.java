package com.vilson.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vilson.table.TableWalls.Wall.CONTENT_ROW;

public class TableFormatter {
    private final int cellWidth;
    private final int padding = 0;
    private final FormatForm formatForm;
    private final List<String> horizontalBorders;

    public TableFormatter(int maxNameLength, int numberOfParticipants) {
        cellWidth = maxNameLength + padding;
        int numberOfColumns = numberOfParticipants + 1;
        formatForm = new FormatForm(numberOfColumns);
        horizontalBorders = initializedHorizontalBorders(numberOfColumns);
    }

    public String getFormattedTable(List<List<String>> tableOfPairs) {
        List<String> content = getContentRows(tableOfPairs);
        List<String> mixedRows = bindContentRowsWithBorders(content);
        return String.join(System.lineSeparator(), mixedRows);
    }

    private List<String> initializedHorizontalBorders(int numberOfColumns) {
        HorizontalBordersProvider horizontalBordersProvider =
                new HorizontalBordersProvider(formatForm, cellWidth, numberOfColumns);
        return horizontalBordersProvider.getHorizontalBorders();
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
