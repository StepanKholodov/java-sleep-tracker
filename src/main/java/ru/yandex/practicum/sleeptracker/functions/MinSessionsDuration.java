package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;

public class MinSessionDuration implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        long minSessionsessionDuration = sessions.
                stream().
                map(session -> {
                    return Duration.between(session.getStart(), session.getEnd()).toMinutes();
        }).min(Long::compareTo).orElse(0L);

        return new SleepAnalysisResult<Long>("Минимальная продолжительность сна в минутах ",
                minSessionsessionDuration);
    }
}
