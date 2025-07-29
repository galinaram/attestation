package com.gridnine.testing;

import java.util.List;

/**
 * Главный класс приложения для демонстрации работы фильтров.
 */
public class Main {
    /**
     * Точка входа в приложение.
     * Создает тестовые данные и демонстрирует работу всех фильтров.
     * @param args Аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        // Получаем тестовые данные
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Все перелёты:");
        flights.forEach(System.out::println);

        // Демонстрация работы каждого фильтра
        demonstrateFilter(flights, new DepartureBeforeCurrentTimeFilter(),
                "Перелёты с вылетом до текущего момента времени:");

        demonstrateFilter(flights, new ArrivalBeforeDepartureFilter(),
                "Перелёты с сегментами, где прилёт раньше вылета:");

        demonstrateFilter(flights, new ExcessiveGroundTimeFilter(),
                "Перелёты с общим временем на земле > 2 часов:");
    }

    /**
     * Вспомогательный метод для демонстрации работы фильтра
     */
    private static void demonstrateFilter(List<Flight> flights,
                                          FlightFilter filter,
                                          String message) {
        System.out.println("\n" + message);
        FlightFilterProcessor.filterFlights(flights, filter)
                .forEach(System.out::println);
    }
}