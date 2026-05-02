package ru.yandex.practicum.sleeptracker.exceptions;

/**
 * Исключение, выбрасываемое при некорректном формате или содержимом строки сессии сна.
 */
public class IncorrectSleepSession extends RuntimeException {

    public IncorrectSleepSession(String message) {
        super(message);
    }

    public IncorrectSleepSession(String message, Throwable cause) {
        super(message, cause);
    }
}