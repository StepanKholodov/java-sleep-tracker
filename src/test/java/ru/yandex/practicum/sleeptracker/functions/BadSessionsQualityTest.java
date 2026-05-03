package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.Condition;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadSessionsQualityTest {
    private final BadSessionsQuality counter = new BadSessionsQuality();

    @Test
    @DisplayName("Пустой список должен вернуть 0 ")
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> emptyList = new ArrayList<>();
        SleepAnalysisResult<Long> result = counter.analyze(emptyList);

        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    @DisplayName("Список из данных трёх сессий должен вернуть 2")
    void shouldReturnCorrectCountForMultipleSessions() {
        List<SleepingSession> sessions = new ArrayList<>();
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 0, 0),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                Condition.GOOD
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 0),
                LocalDateTime.of(2025, 10, 3, 8, 0),
                Condition.BAD
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 14, 30),
                LocalDateTime.of(2025, 10, 3, 15, 21),
                Condition.BAD
        ));

        SleepAnalysisResult<Long> result = counter.analyze(sessions);

        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
        assertEquals(2, result.getValue());
    }
}
