package com.vilson.output;

public interface Output {
    void forwardMessage(String message);

    void forwardIndentedMessage(String message);
}
