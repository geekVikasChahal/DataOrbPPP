package com.example.dataorbppp;

import com.example.dataorbppp.models.Event;
import com.example.dataorbppp.utils.FileReaderUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class FileReaderUtilTest {

    @Test
    public void testReadEventsFromFile_ValidFile() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".csv");
        Files.write(tempFile, "1,emp101,ONBOARD,Software Engineer,01-11-2022,Bill Gates is going to join DataOrb on 1st November as a SE.\n".getBytes());

        List<Event> events = FileReaderUtil.readEventsFromFile(tempFile.toString());
        assertEquals(1, events.size());
        assertEquals("emp101", events.get(0).getEmpId());

        Files.delete(tempFile);
    }

    @Test
    public void testReadEventsFromFile_EmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".csv");

        List<Event> events = FileReaderUtil.readEventsFromFile(tempFile.toString());
        assertTrue(events.isEmpty());

        Files.delete(tempFile);
    }

    @Test
    public void testReadEventsFromFile_FileNotFound() {
        assertThrows(IOException.class, () -> {
            FileReaderUtil.readEventsFromFile("nonexistentfile.csv");
        });
    }
}
