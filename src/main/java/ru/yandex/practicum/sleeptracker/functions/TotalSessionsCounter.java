package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
/**
 * Подсчитывает общее количество сессий сна в предоставленном логе.
 * <p>
 * Простейшая метрика, полезная для базовой оценки активности пользователя.
 * <p>
 * <b>Возвращаемое значение:</b> количество сессий ({@link Integer}).
 *
 * @see SleepAnalysisFunction
 */
public class TotalSessionsCounter implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult<Integer> analyze(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>("Общее количество сессий сна", sessions.size());
    }
}
