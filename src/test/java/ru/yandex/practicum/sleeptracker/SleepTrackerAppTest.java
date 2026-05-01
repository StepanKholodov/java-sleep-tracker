package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.Condition;
import ru.yandex.practicum.sleeptracker.functions.TotalSessionsCounter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {

    private final TotalSessionsCounter counter = new TotalSessionsCounter();

    @Test
    @DisplayName("Пустой список должен вернуть 0 сессий")
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> emptyList = new ArrayList<>();
        SleepAnalysisResult<Integer> result = counter.analyze(emptyList);

        assertEquals("Общее количество сессий сна", result.getDescription());
        assertEquals(0, result.getValue().intValue());
    }

    @Test
    @DisplayName("Список из трёх сессий должен вернуть количество 3")
    void shouldReturnCorrectCountForMultipleSessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 15),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                Condition.GOOD
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 0),
                LocalDateTime.of(2025, 10, 3, 8, 0),
                Condition.NORMAL
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 14, 30),
                LocalDateTime.of(2025, 10, 3, 15, 20),
                Condition.BAD
        ));

        SleepAnalysisResult<Integer> result = counter.analyze(sessions);

        assertEquals("Общее количество сессий сна", result.getDescription());
        assertEquals(3, result.getValue().intValue());
    }
}