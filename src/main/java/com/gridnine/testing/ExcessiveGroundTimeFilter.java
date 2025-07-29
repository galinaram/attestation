package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Фильтр, исключающий перелёты с общим временем на земле более двух часов.
 * Время на земле - это интервалы между прилётом одного сегмента и вылетом следующего.
 */
public class ExcessiveGroundTimeFilter implements FlightFilter {
    // Максимально допустимое время на земле в часах
    private static final long MAX_GROUND_TIME_HOURS = 2;

    /**
     * Проверяет перелёт на превышение допустимого времени на земле
     * @param flight Перелёт для проверки
     * @return true если общее время на земле превышает MAX_GROUND_TIME_HOURS
     */
    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = flight.getSegments();

        // Перелёты с менее чем 2 сегментами не имеют времени на земле
        if (segments.size() < 2) return false;

        long totalGroundTime = 0;

        // Вычисляем суммарное время на земле между всеми сегментами
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime currentArrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();

            // Суммируем часы между сегментами
            totalGroundTime += Duration.between(currentArrival, nextDeparture).toHours();

            // Оптимизация: прерываем проверку при превышении лимита
            if (totalGroundTime > MAX_GROUND_TIME_HOURS) {
                return true;
            }
        }

        return totalGroundTime > MAX_GROUND_TIME_HOURS;
    }
}