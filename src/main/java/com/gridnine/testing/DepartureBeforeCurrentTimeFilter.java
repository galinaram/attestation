package com.gridnine.testing;

import java.time.LocalDateTime;

/**
 * Фильтр, исключающий перелёты с вылетом в прошлом.
 * Перелёт исключается, если хотя бы один его сегмент имеет дату вылета раньше текущего момента.
 */
public class DepartureBeforeCurrentTimeFilter implements FlightFilter {
    /**
     * Проверяет, содержит ли перелёт сегменты с вылетом в прошлом
     * @param flight Перелёт для проверки
     * @return true если перелёт содержит хотя бы один сегмент с вылетом в прошлом
     */
    @Override
    public boolean test(Flight flight) {
        LocalDateTime now = LocalDateTime.now();
        return flight.getSegments().stream()
                .anyMatch(segment -> segment.getDepartureDate().isBefore(now));
    }
}