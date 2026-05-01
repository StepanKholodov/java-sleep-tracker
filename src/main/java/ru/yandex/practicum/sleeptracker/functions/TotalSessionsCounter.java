package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessionsCounter implements SleepAnalysisFunction{
    @Override
    public SleepAnalysisResult<Integer> analyze(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>("Общее количество сессий сна", sessions.size());
    }
}
