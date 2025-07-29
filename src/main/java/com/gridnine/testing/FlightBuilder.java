package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Фабричный класс для создания тестовых наборов перелётов.
 * Содержит предопределённые сценарии перелётов для тестирования фильтров.
 */
public class FlightBuilder {

    /**
     * Создаёт стандартный набор тестовых перелётов, включая:
     * - Нормальные перелёты
     * - Перелёты с вылетом в прошлом
     * - Перелёты с некорректными сегментами
     * - Перелёты с длительным временем на земле
     *
     * @return Список тестовых перелётов
     */
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                // Нормальный перелёт длительностью 2 часа
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),

                // Нормальный перелёт с двумя сегментами
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),

                // Перелёт с вылетом 6 дней назад
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),

                // Некорректный перелёт (прилёт раньше вылета)
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),

                // Перелёт с длительным временем на земле (3 часа)
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),

                // Перелёт с несколькими сегментами и общим временем на земле >2 часов
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    /**
     * Создаёт перелёт из последовательности дат вылета/прилёта.
     *
     * @param dates Массив дат, где каждая пара элементов представляет сегмент:
     *              первый элемент пары - вылет, второй - прилёт
     * @return Созданный перелёт
     * @throws IllegalArgumentException Если передано нечётное количество дат
     */
    private static Flight createFlight(final LocalDateTime... dates) {
        // Проверка на чётное количество дат (каждому вылету нужен прилёт)
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "Необходимо передать чётное количество дат");
        }

        List<Segment> segments = new ArrayList<>(dates.length / 2);

        // Создаём сегменты из пар дат
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }

        return new Flight(segments);
    }
}