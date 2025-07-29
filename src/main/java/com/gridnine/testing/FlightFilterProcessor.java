package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-процессор для фильтрации списка перелётов по заданному условию.
 * Использует Stream API для эффективной обработки больших коллекций.
 */
public class FlightFilterProcessor {
    /**
     * Фильтрует список перелётов по заданному условию
     * @param flights Исходный список перелётов
     * @param filter Реализация интерфейса фильтра
     * @return Отфильтрованный список перелётов
     */
    public static List<Flight> filterFlights(List<Flight> flights, FlightFilter filter) {
        return flights.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }
}