package me.goral.easygraph.generators;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JsonLogger {

    private static final String LOG_FILE = "logs.json";

    public static synchronized void logToJson(String type, String message, String data) {
        String jsonEntry = String.format("{\"type\": \"%s\", \"message\": \"%s\", \"data\": \"%s\"}",
                type, escapeJson(message), escapeJson(data));

        String existingContent = readLogFile();
        String newContent;
        if (existingContent.isEmpty()) {
            newContent = "[" + jsonEntry + "]";
        } else {
            newContent = existingContent.substring(0, existingContent.length() - 1) + "," + jsonEntry + "]";
        }


        try (FileWriter file = new FileWriter(LOG_FILE, false)) {
            file.write(newContent + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private static String readLogFile() {
        try (FileReader fr = new FileReader(LOG_FILE); Scanner scanner = new Scanner(fr)) {
            scanner.useDelimiter("\\Z");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
            return "";
        }
    }

    private static String escapeJson(String data) {
        return data.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
