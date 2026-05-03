package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.Chronotype;
import ru.yandex.practicum.sleeptracker.enums.Condition;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeOfUserTest {

    private final ChronotypeOfUser function = new ChronotypeOfUser();

    @Test
    @DisplayName("Пустой список – возвращается голубь")
    void emptyListReturnsDove() {
        SleepAnalysisResult<Chronotype> result = function.analyze(new ArrayList<>());
        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    @DisplayName("Единственная ночная сессия-сова")
    void singleOwlSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        Condition.GOOD
                )
        );
        assertEquals(Chronotype.NIGHT_OWL, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Единственная ночная сессия-жаворонок")
    void singleLarkSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        Condition.NORMAL
                )
        );
        assertEquals(Chronotype.MORNING_LARK, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Единственная ночная сессия-голубь")
    void singleDoveSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        Condition.BAD
                )
        );
        assertEquals(Chronotype.DOVE, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Дневная сессия игнорируется")
    void daySessionIgnored() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        Condition.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        Condition.GOOD
                )
        );
        assertEquals(Chronotype.NIGHT_OWL, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Преобладание сов над жаворонками")
    void owlMajority() {
        List<SleepingSession> sessions = List.of(
                owlSession(1), owlSession(2), owlSession(3),
                larkSession(4)
        );
        assertEquals(Chronotype.NIGHT_OWL, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Равное количество сов и жаворонков – голубь")
    void tieReturnsDove() {
        List<SleepingSession> sessions = List.of(
                owlSession(1), owlSession(2),
                larkSession(3), larkSession(4)
        );
        assertEquals(Chronotype.DOVE, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Граничные значения: 23:00 и 9:00 считаются голубем")
    void boundaryTimesAreDove() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 9, 0),
                        Condition.GOOD
                )
        );
        assertEquals(Chronotype.DOVE, function.analyze(sessions).getValue());
    }

    @Test
    @DisplayName("Граничные значения жаворонка: 22:00 и 7:00 – голубь")
    void boundaryLarkTimesAreDove() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        Condition.BAD
                )
        );
        assertEquals(Chronotype.DOVE, function.analyze(sessions).getValue());
    }

    private SleepingSession owlSession(int id) {
        return new SleepingSession(
                LocalDateTime.of(2025, 10, id, 23, 30),
                LocalDateTime.of(2025, 10, id + 1, 10, 0),
                Condition.GOOD
        );
    }

    private SleepingSession larkSession(int id) {
        return new SleepingSession(
                LocalDateTime.of(2025, 10, id, 21, 0),
                LocalDateTime.of(2025, 10, id + 1, 6, 30),
                Condition.GOOD
        );
    }
}