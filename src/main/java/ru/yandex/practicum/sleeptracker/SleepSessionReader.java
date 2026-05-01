package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.enums.Condition;
import ru.yandex.practicum.sleeptracker.exceptions.IncorrectSleepSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Утилитный класс для чтения и парсинга файла с логом сна.
 * <p>
 * Ожидаемый формат каждой строки файла:
 * <pre>{@code
 * dd.MM.yy HH:mm;dd.MM.yy HH:mm;QUALITY
 * }</pre>
 * Где:
 * <ul>
 *   <li>{@code dd.MM.yy HH:mm} — дата и время засыпания</li>
 *   <li>{@code dd.MM.yy HH:mm} — дата и время пробуждения</li>
 *   <li>{@code QUALITY} — одно из значений: {@code GOOD}, {@code NORMAL}, {@code BAD}</li>
 * </ul>
 * <p>
 * Пример строки: {@code "01.10.25 22:15;02.10.25 08:00;GOOD"}
 *
 * @see SleepingSession
 * @see ru.yandex.practicum.sleeptracker.enums.Condition
 */
public class SleepSessionReader {

    /**
     * Формат даты и времени, соответствующий спецификации файла лога.
     * <p>
     * Используется {@link java.time.format.DateTimeFormatter} для парсинга
     * строк в объекты {@link java.time.LocalDateTime}.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    /**
     * Читает файл по указанному пути и возвращает список сессий сна.
     * <p>
     * Метод использует Stream API для эффективной обработки файла построчно.
     * Пустые строки игнорируются.
     *
     * @param filePath путь к файлу с логом сна
     * @return список объектов {@link SleepingSession}, отсортированных по времени
     * @throws IOException            если файл не найден или не может быть прочитан
     * @throws IncorrectSleepSession  если строка файла имеет некорректный формат
     * @throws DateTimeParseException если дата/время не соответствуют ожидаемому формату
     */
    public static List<SleepingSession> read(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {

            return lines.filter(line -> !line.isBlank()).
                    map(SleepSessionReader::parseLine).
                    collect(Collectors.toList());
        }
    }

    /**
     * Парсит одну строку лога в объект {@link SleepingSession}.
     * <p>
     * Метод приватный, используется только внутри {@code read()}.
     *
     * @param line строка формата "старт;конец;качество"
     * @return объект сессии сна
     * @throws IncorrectSleepSession  если строка содержит не 3 поля
     * @throws DateTimeParseException если не удалось распарсить дату/время
     */
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
