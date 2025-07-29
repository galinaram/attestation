package com.gridnine.testing;

/**
 * Фильтр для выявления перелётов с некорректными сегментами,
 * где время прилёта раньше времени вылета.
 * Такие ситуации физически невозможны и указывают на ошибку в данных.
 */
public class ArrivalBeforeDepartureFilter implements FlightFilter {

    /**
     * Проверяет перелёт на наличие сегментов с прилётом раньше вылета.
     *
     * @param flight Перелёт для проверки
     * @return true если перелёт содержит хотя бы один некорректный сегмент,
     *         false если все сегменты валидны
     */
    @Override
    public boolean test(Flight flight) {
        return flight.getSegments().stream()
                // Проверяем каждый сегмент на аномалию (прилёт < вылет)
                .anyMatch(segment -> segment.getArrivalDate()
                        .isBefore(segment.getDepartureDate()));
    }
}