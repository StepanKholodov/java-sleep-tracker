package ru.yandex.practicum.sleeptracker;

public class IncorrectSleepSession extends RuntimeException{
    public IncorrectSleepSession(String message) {
        super(message);
    }
}
