package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisFunction;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.Chronotype;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ChronotypeOfUser implements SleepAnalysisFunction {

    /**
     * Определяет хронотип пользователя на основе статистики ночных сессий.
     * <p>
     * Классификация одной ночи:
     * <ul>
     *   <li><b>Сова</b>: засыпание строго после 23:00 И пробуждение строго после 09:00</li>
     *   <li><b>Жаворонок</b>: засыпание строго до 22:00 И пробуждение строго до 07:00</li>
     *   <li><b>Голубь</b>: все остальные случаи</li>
     * </ul>
     * Дневные сессии (не пересекающие 00:00–06:00) игнорируются.
     * При равенстве типов или пустом списке возвращается {@link Chronotype#DOVE}.
     *
     * @return результат с определённым хронотипом пользователя
     */
    @Override
    public SleepAnalysisResult<Chronotype> analyze(List<SleepingSession> sessions) {
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(this::isNightSession)
                .toList();

        long owlCount = nightSessions.stream()
                .filter(s -> s.getStart().toLocalTime().isAfter(LocalTime.of(23, 0))
                        && s.getEnd().toLocalTime().isAfter(LocalTime.of(9, 0)))
                .count();

        long larkCount = nightSessions.stream()
                .filter(s -> s.getStart().toLocalTime().isBefore(LocalTime.of(22, 0))
                        && s.getEnd().toLocalTime().isBefore(LocalTime.of(7, 0)))
                .count();

        long doveCount = nightSessions.size() - owlCount - larkCount;

        Chronotype resultType;
        if (owlCount > larkCount && owlCount > doveCount) {
            resultType = Chronotype.NIGHT_OWL;
        } else if (larkCount > owlCount && larkCount > doveCount) {
            resultType = Chronotype.MORNING_LARK;
        } else {
            resultType = Chronotype.DOVE;
        }

        return new SleepAnalysisResult<Chronotype>("Хронотип пользователя", resultType);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDate startDate = session.getStart().toLocalDate();
        LocalDate endDate = session.getEnd().toLocalDate();

        // проверяем, перекрывает ли сессия интервал 00:00–06:00 на дату начала
        if (session.getStart().isBefore(startDate.atTime(6, 0))
                && session.getEnd().isAfter(startDate.atStartOfDay())) {
            return true;
        }

        // если даты разные, проверяем на дату конца
        if (!startDate.equals(endDate)) {
            return session.getStart().isBefore(endDate.atTime(6, 0))
                    && session.getEnd().isAfter(endDate.atStartOfDay());
        }
        return false;
    }
}