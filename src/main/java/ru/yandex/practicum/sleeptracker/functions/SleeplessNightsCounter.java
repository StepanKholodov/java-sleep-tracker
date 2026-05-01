package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;

public class SleeplessNightsCounter implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0L);
        }

        // Находим глобальные начало и конец периода логирования
        LocalDateTime firstStart = sessions.stream()
                .map(SleepingSession::getStart)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        LocalDateTime lastEnd = sessions.stream()
                .map(SleepingSession::getEnd)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        // Определяем первую потенциальную ночь по правилу "до/после 12:00"
        LocalDate firstNightDate;

        if (firstStart.toLocalTime().isBefore(LocalTime.NOON)) {
            firstNightDate = firstStart.toLocalDate();
        } else {
            firstNightDate = firstStart.toLocalDate().plusDays(1);
        }

        // Последняя потенциальная ночь – это календарная дата окончания последней сессии
        LocalDate lastNightDate = lastEnd.toLocalDate();

        // Общее количество ночей в интервале (включительно)
        long totalNights = ChronoUnit.DAYS.between(firstNightDate, lastNightDate) + 1L;
        if (totalNights <= 0) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0L);
        }

        // Считаем, сколько из этих ночей покрыты хотя бы одной сессией
        long coveredNights = LongStream.rangeClosed(0, totalNights - 1)
                .mapToObj(firstNightDate::plusDays)
                .filter(date -> isNightCovered(sessions, date))
                .count();

        long sleeplessNights = totalNights - coveredNights;
        return new SleepAnalysisResult<>("Количество бессонных ночей", sleeplessNights);
    }

    /**
     * Проверяет, покрывает ли хотя бы одна сессия интервал 00:00 – 06:00 указанной даты.
     */
    private boolean isNightCovered(List<SleepingSession> sessions, LocalDate date) {
        LocalDateTime nightStart = date.atStartOfDay();
        LocalDateTime nightEnd = date.atTime(LocalTime.of(6, 0));

        return sessions.stream().anyMatch(session ->
                session.getStart().isBefore(nightEnd) && session.getEnd().isAfter(nightStart)
        );
    }
}