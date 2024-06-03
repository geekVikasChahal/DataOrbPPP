package com.example.dataorbppp.utils;

import java.io.*;
import java.util.*;
import com.example.dataorbppp.models.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FileReaderUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static List<Event> readEventsFromFile(String filePath) throws IOException {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine())!= null) {
                String[] parts = line.split(",");
                if (parts.length!= 6) {
                    throw new IllegalArgumentException("Invalid input format: " + line);
                }
                int sequenceNo = Integer.parseInt(parts[0].trim());
                String empId = parts[1].trim();
                String eventType = parts[2].trim();
                String value = parts[3].trim();
                Date eventDate;
                try {
                    eventDate = sdf.parse(parts[4].trim());
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Failed to parse date: " + parts[4].trim(), e);
                }
                String notes = parts[5].trim();
                events.add(new Event(sequenceNo, empId, eventType, value, eventDate, notes));
            }
        }
        return events;
    }
}