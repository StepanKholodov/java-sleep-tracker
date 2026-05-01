package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.enums.Condition;

import java.time.LocalDateTime;

/**
 * Представляет одну сессию сна с временными метками и оценкой качества.
 * <p>
 * Сессия определяется моментом засыпания ({@code start}),
 * моментом пробуждения ({@code end}) и качеством сна ({@code condition}).
 */

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Condition condition;

    /**
     * Создаёт новую сессию сна.
     * @param start время засыпания, не может быть null
     * @param end время пробуждения, не может быть null
     * @param condition качество сна, не может быть null
     * @throws IllegalArgumentException если start >= end
     */
    public SleepingSession(LocalDateTime start, LocalDateTime end, Condition condition) {
        this.start = start;
        this.end = end;
        this.condition = condition;
    }

    /**
     * @return время начала сессии
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return время окончания сессии
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return оценка качества сна
     */
    public Condition getCondition() {
        return condition;
    }
}
