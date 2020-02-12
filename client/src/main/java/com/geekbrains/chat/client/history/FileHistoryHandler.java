package com.geekbrains.chat.client.history;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileHistoryHandler implements HistoryHandler {
    private File historyFile;

    public FileHistoryHandler(String login) {
        this.historyFile = new File("history_" + login + ".txt");
    }

    @Override
    public void addRecord(String msg) {
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(historyFile, true), StandardCharsets.UTF_8)) {
            out.write((msg + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getHistory(int historySize) {
        List<String> list = new ArrayList<>();
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(historyFile), StandardCharsets.UTF_8)) {
            int x;
            StringBuilder sb = new StringBuilder();
            while ((x = fileReader.read()) != -1) {
                sb.append((char) x);
                if (x == '\n') {
                    list.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (historySize < list.size() - 1) {
            return list.subList(list.size() - historySize, list.size());
        } else {
            return list;
        }

    }
}
