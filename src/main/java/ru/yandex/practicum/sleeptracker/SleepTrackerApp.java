package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {
    private static final Logger logger = Logger.getLogger(SleepTrackerApp.class.getName());

    private static final List<SleepAnalysisFunction>
            ANALYSIS_FUNCTIONS = new ArrayList<>();

    static {
        ANALYSIS_FUNCTIONS.add(new TotalSessionsCounter());
        ANALYSIS_FUNCTIONS.add(new MinSessionsDuration());
        ANALYSIS_FUNCTIONS.add(new MaxSessionsDuration());
        ANALYSIS_FUNCTIONS.add(new AvgSessionsDuration());
        ANALYSIS_FUNCTIONS.add(new BadSessionsQuality());



    }

    public static void main(String[] args)  {
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
        }catch (IncorrectSleepSession | DateTimeParseException e) {
            logger.log(Level.SEVERE, "Файл содержит ошибки: " + e.getMessage());
            return;
        }


        ANALYSIS_FUNCTIONS.forEach(func -> {
            SleepAnalysisResult<?> result = func.analyze(sessions);
            System.out.println(result);
        });
    }
}