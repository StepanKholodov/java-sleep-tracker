package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.enums.Condition;

import java.time.LocalDateTime;
import java.util.Map;

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Condition condition;

    public SleepingSession(LocalDateTime start, LocalDateTime end, Condition condition) {
        this.start = start;
        this.end = end;
        this.condition = condition;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Condition getCondition() {
        return condition;
    }
}
