package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.Condition;
import ru.yandex.practicum.sleeptracker.functions.SleeplessNightsCounter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsCounterTest {

    private final SleeplessNightsCounter counter = new SleeplessNightsCounter();

    @Test
    @DisplayName("Пустой список сессий – 0 бессонных ночей")
    void shouldReturnZeroForEmptyList() {
        SleepAnalysisResult<Long> result = counter.analyze(new ArrayList<>());
        assertEquals(0L, result.getValue().longValue());
    }

    @Test
    @DisplayName("Одна ночная сессия покрывает единственную ночь – 0 бессонных")
    void singleSessionCoveringNight() {
        // Сессия 1 Oct 23:00 -> 2 Oct 03:00 (начало после 12 -> firstNightDate = 2 Oct)
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        Condition.GOOD
                )
        );
        SleepAnalysisResult<Long> result = counter.analyze(sessions);
        assertEquals(0L, result.getValue().longValue());
    }

    @Test
    @DisplayName("Сессия после 6 утра (не покрывает ночь) – одна бессонная ночь")
    void sessionNotCoveringNight() {
        // Сессия 1 Oct 7:00 -> 11:00 (начало до 12 -> firstNightDate = 1 Oct)
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        Condition.NORMAL
                )
        );
        SleepAnalysisResult<Long> result = counter.analyze(sessions);
        assertEquals(1L, result.getValue().longValue());
    }

    @Test
    @DisplayName("Две ночи, одна покрыта, одна нет")
    void twoNightsOneSleepless() {
        // Сессия покрывает ночь 2 Oct (23:00 1 Oct - 3:00 2 Oct)
        // Период: с 1 Oct 23:00 по 3 Oct 10:00 (lastEnd определяет ночь 3 Oct)
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        Condition.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 7, 0),   // эта сессия не покрывает ночь 3 Oct
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        Condition.BAD
                )
        );
        // firstStart = 1 Oct 23:00 (после 12) -> firstNightDate = 2 Oct
        // lastEnd = 3 Oct 10:00 -> lastNightDate = 3 Oct
        // totalNights = 2 (2 и 3 октября). Покрыта ночь 2 Oct, ночь 3 Oct не покрыта -> бессонных 1
        SleepAnalysisResult<Long> result = counter.analyze(sessions);
        assertEquals(1L, result.getValue().longValue());
    }

    @Test
    @DisplayName("Переход через месяц и правило firstNightDate")
    void transitionOverMonthAndNoonRule() {
        // Сессия 31 Oct 14:00 - 16:00 (начало после 12) -> firstNightDate = 1 Nov
        // lastEnd = 2 Nov 9:00 -> lastNightDate = 2 Nov
        // Ночи: 1 Nov и 2 Nov. Сессий, покрывающих их, нет.
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 31, 14, 0),
                        LocalDateTime.of(2025, 10, 31, 16, 0),
                        Condition.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 11, 2, 8, 0),
                        LocalDateTime.of(2025, 11, 2, 9, 0),
                        Condition.GOOD
                )
        );
        SleepAnalysisResult<Long> result = counter.analyze(sessions);
        assertEquals(2L, result.getValue().longValue());
    }
}