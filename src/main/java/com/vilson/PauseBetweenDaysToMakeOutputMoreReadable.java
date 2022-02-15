package com.vilson;

import com.vilson.output.Output;

import java.io.Console;

class PauseBetweenDaysToMakeOutputMoreReadable {

    private final Output output;
    private final Console console;

    PauseBetweenDaysToMakeOutputMoreReadable(Output output) {
        this.output = output;
        console = System.console();
    }

    void untilEnter(int day) {
        output.forwardMessage(String.format("Press Enter to proceed with the day %d.", day));
        pauseOnlyInOrderToMakeTheOutputMoreReadableAndFineIfNotPresent();
    }

    private void pauseOnlyInOrderToMakeTheOutputMoreReadableAndFineIfNotPresent() {
        if (console == null) {
            output.forwardMessage("Console is not available to current JVM process");
            return;
        }

        console.readLine();
    }
}
