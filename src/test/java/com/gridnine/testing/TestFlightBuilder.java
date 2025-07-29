package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для создания тестовых перелётов.
 * Содержит фабричные методы для удобного создания различных сценариев перелётов.
 */
public class TestFlightBuilder {

    /**
     * Создает перелёт из последовательности дат вылета/прилёта.
     *
     * @param dates Массив дат, где четные элементы - вылеты, нечетные - прилёты
     * @return Созданный перелёт
     * @throws IllegalArgumentException Если передано нечетное количество дат
     */
    public static Flight createTestFlight(LocalDateTime... dates) {
        // Проверка на четное количество дат (каждому вылету нужен прилёт)
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException("Must pass even number of dates");
        }

        List<Segment> segments = new ArrayList<>();

        // Создаем сегменты: i - вылет, i+1 - прилёт
        for (int i = 0; i < dates.length; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }

        return new Flight(segments);
    }

    /**
     * Создает перелёт с заданным временем на земле между сегментами.
     *
     * @param hours Время на земле в часах
     * @return Перелёт с двумя сегментами и указанным временем на земле
     */
    public static Flight createFlightWithGroundTime(long hours) {
        LocalDateTime now = LocalDateTime.now();
        return createTestFlight(
                now, now.plusHours(1),                     // Первый сегмент (1 час в воздухе)
                now.plusHours(1 + hours), now.plusHours(2 + hours) // Второй сегмент
        );
    }
}