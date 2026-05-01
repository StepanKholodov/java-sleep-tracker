package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;

public class AvgSessionsDuration implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<Double> analyze(List<SleepingSession> sessions) {
        double avgMinutes = sessions.stream()
                .mapToLong(session -> Duration.between(session.getStart(), session.getEnd())
                        .toMinutes())
                .average()
                .orElse(0);

        return new SleepAnalysisResult<Double>("Средняя продолжительность сна в минутах", avgMinutes);
    }
}
