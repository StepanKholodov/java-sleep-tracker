package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;

public class MaxSessionsDuration implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        long maxSessionsDuration = sessions.
                stream().
                map(session -> {
                    return Duration.between(session.getStart(), session.getEnd()).toMinutes();
                }).max(Long::compareTo).orElse(0L);

        return new SleepAnalysisResult<Long>("Максимальная продолжительность сна в минутах",
                maxSessionsDuration);
    }
}
