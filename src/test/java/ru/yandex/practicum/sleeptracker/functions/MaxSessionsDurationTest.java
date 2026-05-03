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

public class MaxSessionsDurationTest {
    private final MaxSessionsDuration counter = new MaxSessionsDuration();

    @Test
    @DisplayName("Пустой список должен вернуть 0 ")
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> emptyList = new ArrayList<>();
        SleepAnalysisResult<Long> result = counter.analyze(emptyList);

        assertEquals("Максимальная продолжительность сна в минутах", result.getDescription());
        assertEquals(0, result.getValue().intValue());
    }

    @Test
    @DisplayName("Список из данных трёх сессий должен вернуть 540")
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
                Condition.NORMAL
        ));
        sessions.add(new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 14, 30),
                LocalDateTime.of(2025, 10, 3, 15, 20),
                Condition.BAD
        ));

        SleepAnalysisResult<Long> result = counter.analyze(sessions);

        assertEquals("Максимальная продолжительность сна в минутах", result.getDescription());
        assertEquals(540, result.getValue().intValue());
    }
}
