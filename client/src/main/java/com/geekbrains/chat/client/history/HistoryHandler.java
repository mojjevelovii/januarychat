package com.geekbrains.chat.client.history;

import java.util.List;

public interface HistoryHandler {
    void addRecord(String msg);

    List<String> getHistory(int historySize);

}
