package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.Condition;

import java.time.Duration;
import java.util.List;

public class BadSessionsQuality implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        long countSessions = sessions.
                stream().filter(session -> session.getCondition().equals(Condition.BAD)).count();

        return new SleepAnalysisResult<Long>("Количество сессий с плохим качеством сна",
                countSessions);
    }
}
