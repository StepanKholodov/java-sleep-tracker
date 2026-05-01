package ru.yandex.practicum.sleeptracker.exceptions;

public class IncorrectSleepSession extends RuntimeException{
    public IncorrectSleepSession(String message) {
        super(message);
    }
}
