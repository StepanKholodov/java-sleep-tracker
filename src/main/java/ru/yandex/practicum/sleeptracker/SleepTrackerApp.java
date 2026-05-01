

package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.exceptions.IncorrectSleepSession;
import ru.yandex.practicum.sleeptracker.functions.*;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Основное приложение для анализа данных о сне.
 * <p>
 * Приложение принимает путь к файлу с логом сна в качестве аргумента командной строки,
 * загружает данные, выполняет все зарегистрированные аналитические функции и выводит
 * результаты в консоль.
 * <p>
 * Архитектура приложения позволяет легко добавлять новые функции анализа без изменения
 * основного кода — достаточно реализовать интерфейс {@link SleepAnalysisFunction} и
 * добавить экземпляр в список {@code ANALYSIS_FUNCTIONS}.
 * <p>
 * <b>Важно:</b> В соответствии с требованиями производителя часов, в коде приложения
 * не используются традиционные циклы {@code for}/{@code while} — только Stream API.
 * Исключение составляют юнит-тесты.
 *
 * @author Kholodov Stepan
 * @version 1.0
 */
public class SleepTrackerApp {
    /**
     * Логгер для записи ошибок приложения.
     */
    private static final Logger logger = Logger.getLogger(SleepTrackerApp.class.getName());

    /**
     * Статический список всех аналитических функций, доступных в приложении.
     * <p>
     * Функции выполняются последовательно в порядке добавления в список.
     */
    private static final List<SleepAnalysisFunction>
            ANALYSIS_FUNCTIONS = new ArrayList<>();

    static {
        ANALYSIS_FUNCTIONS.add(new TotalSessionsCounter());
        ANALYSIS_FUNCTIONS.add(new MinSessionsDuration());
        ANALYSIS_FUNCTIONS.add(new MaxSessionsDuration());
        ANALYSIS_FUNCTIONS.add(new AvgSessionsDuration());
        ANALYSIS_FUNCTIONS.add(new BadSessionsQuality());
        ANALYSIS_FUNCTIONS.add(new SleeplessNightsCounter());
        ANALYSIS_FUNCTIONS.add(new ChronotypeOfUser());

    }

    /**
     * Точка входа в приложение.
     * <p>
     * Алгоритм работы:
     * <ol>
     *   <li>Проверяет наличие аргумента командной строки с путём к файлу</li>
     *   <li>Читает и парсит файл с помощью {@link SleepSessionReader}</li>
     *   <li>Последовательно выполняет все функции из {@code ANALYSIS_FUNCTIONS}</li>
     *   <li>Выводит результаты в консоль через {@code System.out.println}</li>
     * </ol>
     *
     * @param args аргументы командной строки; ожидается один аргумент — путь к файлу
     */
    public static void main(String[] args) {
        List<SleepingSession> sessions;

        if (args.length == 0) {
            System.err.println("Укажите путь к файлу с логом сна в качестве аргумента командной строки.");
            return;
        }


        try {
            sessions = SleepSessionReader.read(Path.of(args[0]));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при чтении файла ", e);
            return;
        } catch (IncorrectSleepSession | DateTimeParseException e) {
            logger.log(Level.SEVERE, "Файл содержит ошибки: " + e.getMessage());
            return;
        }


        ANALYSIS_FUNCTIONS.forEach(func -> {
            SleepAnalysisResult<?> result = func.analyze(sessions);
            System.out.println(result);
        });
    }
}