package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.practicum.sleeptracker.enums.Condition;
import ru.yandex.practicum.sleeptracker.exceptions.IncorrectSleepSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepSessionReaderTest {

    @Test
    @DisplayName("Корректный файл должен парситься без ошибок и возвращать правильные сессии")
    void shouldParseValidFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("valid_sleep_log.txt");
        String content = """
                01.10.25 22:15;02.10.25 08:00;GOOD
                02.10.25 23:00;03.10.25 08:00;NORMAL
                03.10.25 14:30;03.10.25 15:20;NORMAL
                """;
        Files.writeString(file, content);

        List<SleepingSession> sessions = SleepSessionReader.read(file);

        assertEquals(3, sessions.size(), "Должно быть 3 сессии");

        SleepingSession first = sessions.get(0);
        assertEquals(LocalDateTime.of(2025, 10, 1, 22, 15), first.getStart());
        assertEquals(LocalDateTime.of(2025, 10, 2, 8, 0), first.getEnd());
        assertEquals(Condition.GOOD, first.getCondition());

        SleepingSession second = sessions.get(1);
        assertEquals(LocalDateTime.of(2025, 10, 2, 23, 0), second.getStart());
        assertEquals(LocalDateTime.of(2025, 10, 3, 8, 0), second.getEnd());
        assertEquals(Condition.NORMAL, second.getCondition());
    }

    @Test
    @DisplayName("Некорректная строка (не 3 поля) должна вызывать IncorrectSleepSession")
    void shouldThrowExceptionForInvalidLine(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("invalid_sleep_log.txt");
        String content = "01.10.25 22:15;02.10.25 08:00\n";
        Files.writeString(file, content);

        IncorrectSleepSession exception = assertThrows(
                IncorrectSleepSession.class,
                () -> SleepSessionReader.read(file),
                "Должно выброситься IncorrectSleepSession"
        );
        assertTrue(exception.getMessage().contains("некорректный формат строки"));
    }
}