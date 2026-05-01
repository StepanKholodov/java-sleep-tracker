package ru.yandex.practicum.sleeptracker;
/**
 * Универсальная обёртка для результата аналитической функции.
 * <p>
 * Позволяет вернуть не только вычисленное значение, но и его текстовое описание,
 * что упрощает вывод результатов пользователю без привязки к конкретной метрике.
 * <p>
 * Пример использования:
 * <pre>{@code
 * SleepAnalysisResult<Integer> result =
 *     new SleepAnalysisResult<>("Общее количество сессий", 42);
 * System.out.println(result); // Вывод: "Общее количество сессий: 42"
 * }</pre>
 *
 * @param <T> тип возвращаемого значения (например, {@link Integer}, {@link Double},
 *            {@link ru.yandex.practicum.sleeptracker.enums.Chronotype})
 * @see SleepAnalysisFunction
 */
public class SleepAnalysisResult <T>{
    private final String description;
    private final T value;

    /**
     * Создаёт новый результат анализа.
     *
     * @param description текстовое описание метрики, не может быть {@code null}
     * @param value вычисленное значение, может быть {@code null} для некоторых типов
     */
    public SleepAnalysisResult(String description, T value) {
        this.description = description;
        this.value = value;
    }

    /**
     * @return текстовое описание результата
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return вычисленное значение метрики
     */
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return description + ": " + value;
    }
}

