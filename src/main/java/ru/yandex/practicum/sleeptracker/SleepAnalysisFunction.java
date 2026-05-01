package ru.yandex.practicum.sleeptracker;

import java.util.List;

/**
 * Функциональный интерфейс для аналитических функций анализа сна.
 * <p>
 * Каждая реализация этого интерфейса инкапсулирует одну метрику или правило
 * анализа данных о сне. Функции должны быть:
 * <ul>
 *   <li>Независимыми друг от друга</li>
 *   <li>Легко тестируемыми</li>
 *   <li>Возвращать результат в обёртке {@link SleepAnalysisResult}</li>
 * </ul>
 * <p>
 * Пример реализации:
 * <pre>{@code
 * public class TotalSessionsCounter implements SleepAnalysisFunction {
 *     @Override
 *     public SleepAnalysisResult<Integer> analyze(List<SleepingSession> sessions) {
 *         return new SleepAnalysisResult<Integer>("Всего сессий", sessions.size());
 *     }
 * }
 * }</pre>
 *
 * @see SleepAnalysisResult
 * @see SleepingSession
 */
@FunctionalInterface
public interface SleepAnalysisFunction {
    /**
     * Анализирует список сессий сна и возвращает результат.
     * <p>
     * Метод не должен модифицировать входной список.
     *
     * @param sessions список сессий для анализа; предполагается, что не {@code null}
     * @return результат анализа с описанием и вычисленным значением
     */
    SleepAnalysisResult<?> analyze(List<SleepingSession> sessions);
}