package com.vilson.output;

public class ConsoleOutput implements Output {
    private final String indent;

    public ConsoleOutput(int spacesInIndentation) {
        indent = " ".repeat(spacesInIndentation);
    }

    @Override
    public void forwardMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void forwardIndentedMessage(String message) {
        message.lines().map(this::prependIndentation).forEachOrdered(this::forwardMessage);
    }

    private String prependIndentation(String message) {
        return indent + message;
    }
}
