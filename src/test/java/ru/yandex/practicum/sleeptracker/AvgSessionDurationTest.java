package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.Condition;
import ru.yandex.practicum.sleeptracker.functions.AvgSessionsDuration;
import ru.yandex.practicum.sleeptracker.functions.MaxSessionsDuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvgSessionDurationTest {
    private final AvgSessionsDuration counter = new AvgSessionsDuration();

    @Test
    @DisplayName("Пустой список должен вернуть 0 ")
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> emptyList = new ArrayList<>();
        SleepAnalysisResult<Double> result = counter.analyze(emptyList);

        assertEquals("Средняя продолжительность сна в минутах", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    @DisplayName("Список из данных трёх сессий должен вернуть 357")
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
                LocalDateTime.of(2025, 10, 3, 15, 21),
                Condition.BAD
        ));

        SleepAnalysisResult<Double> result = counter.analyze(sessions);

        assertEquals("Средняя продолжительность сна в минутах", result.getDescription());
        assertEquals(357, result.getValue());
    }
}
