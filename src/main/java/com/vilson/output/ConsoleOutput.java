package com.vilson.output;

public class ConsoleOutput implements Output {
    @Override
    public void forwardMessage(String message) {
        System.out.println(message);
    }
}
