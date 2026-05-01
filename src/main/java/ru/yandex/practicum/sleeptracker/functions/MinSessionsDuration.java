package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;

/**
 * Находит минимальную продолжительность сессии сна.
 * <p>
 * Продолжительность вычисляется в минутах как разница между временем
 * пробуждения и засыпания.
 * <p>
 * <b>Возвращаемое значение:</b> минимальная длительность в минутах ({@link Long}).
 * Для пустого списка возвращается {@code 0}.
 *
 * @see Duration
 * @see SleepingSession
 */
public class MinSessionsDuration implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        long minSessionsDuration = sessions
                .stream()
                .map(session -> Duration.between(session.getStart(), session.getEnd()).toMinutes())
                .min(Long::compareTo).orElse(0L);

        return new SleepAnalysisResult<Long>("Минимальная продолжительность сна в минутах",
                minSessionsDuration);
    }
}
