package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.enums.Condition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepSessionReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");


    public static List<SleepingSession> read(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {

                return lines.filter(line -> !line.isBlank()).
                        map(SleepSessionReader::parseLine).
                        collect(Collectors.toList());
        }
    }

    private static SleepingSession parseLine(String line) {
        String[] split = line.split(";");

        if (split.length != 3) {
            throw new IncorrectSleepSession("некорректный формат строки - " + line);
        }

        LocalDateTime start = LocalDateTime.parse(split[0].trim(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(split[1].trim(), FORMATTER);


        return new SleepingSession(start, end, Condition.valueOf(split[2].toUpperCase()));
    }
}
