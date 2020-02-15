package com.geekbrains.chat.client.history;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(historyFile)))) {
            String x;
            while ((x = fileReader.readLine()) != null) {
                list.add(x+"\n");
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
