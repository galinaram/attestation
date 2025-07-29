package com.gridnine.testing;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки фильтров перелётов.
 */
class FlightFilterTest {
    // Фиксируем текущее время для согласованных тестов
    private final LocalDateTime now = LocalDateTime.now();

    /**
     * Тест фильтра перелётов с вылетом в прошлом.
     * Проверяет:
     * 1. Перелёт с сегментом в прошлом должен быть отфильтрован
     * 2. Перелёт только с будущими сегментами не должен фильтроваться
     */
    @Test
    void testDepartureBeforeCurrentTimeFilter() {
        // Подготовка данных
        Flight pastFlight = TestFlightBuilder.createTestFlight(
                now.minusDays(1), now.plusHours(2)); // Вылет в прошлом

        Flight futureFlight = TestFlightBuilder.createTestFlight(
                now.plusDays(1), now.plusDays(2)); // Все сегменты в будущем

        // Действие
        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(pastFlight, futureFlight),
                new DepartureBeforeCurrentTimeFilter());

        // Проверки
        assertEquals(1, filtered.size(), "Должен остаться только 1 перелёт");
        assertTrue(filtered.contains(pastFlight), "Должен быть отфильтрован перелёт с вылетом в прошлом");
    }

    /**
     * Тест фильтра некорректных сегментов (прилёт раньше вылета).
     */
    @Test
    void testArrivalBeforeDepartureFilter() {
        // Нормальный сегмент
        Flight normalFlight = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2));

        // Некорректный сегмент (прилёт раньше вылета)
        Flight invalidFlight = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.minusHours(1));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(normalFlight, invalidFlight),
                new ArrivalBeforeDepartureFilter());

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(invalidFlight));
    }

    /**
     * Тест фильтра чрезмерного времени на земле (>2 часов).
     * Проверяет:
     * 1. Перелёт с допустимым временем (1 час)
     * 2. Перелёт с чрезмерным временем (3 часа)
     */
    @Test
    void testExcessiveGroundTimeFilter() {
        // Перелёт с 1 часом на земле (должен пройти фильтр)
        Flight acceptableFlight = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(4));

        // Перелёт с 3 часами на земле (должен быть отфильтрован)
        Flight excessiveFlight = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(5), now.plusHours(6));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(acceptableFlight, excessiveFlight),
                new ExcessiveGroundTimeFilter());

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(excessiveFlight));
    }

    /**
     * Тест обработки пустого списка перелётов.
     * Проверяет, что фильтры не падают на пустом вводе.
     */
    @Test
    void testEmptyFlightList() {
        List<Flight> emptyList = List.of();

        assertTrue(FlightFilterProcessor.filterFlights(
                emptyList, new DepartureBeforeCurrentTimeFilter()).isEmpty());

        assertTrue(FlightFilterProcessor.filterFlights(
                emptyList, new ArrivalBeforeDepartureFilter()).isEmpty());

        assertTrue(FlightFilterProcessor.filterFlights(
                emptyList, new ExcessiveGroundTimeFilter()).isEmpty());
    }

    /**
     * Тест перелёта с одним сегментом.
     * Проверяет, что фильтр времени на земле корректно обрабатывает
     * перелёты без промежуточных посадок.
     */
    @Test
    void testSingleSegmentFlight() {
        Flight singleSegment = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(singleSegment),
                new ExcessiveGroundTimeFilter());

        assertTrue(filtered.isEmpty(),
                "Перелёты с одним сегментом не должны фильтроваться");
    }

    /**
     * Тест граничного случая - ровно 2 часа на земле.
     * Проверяет, что фильтр не отбрасывает перелёты с точно 2 часами на земле.
     */
    @Test
    void testExactTwoHoursGroundTime() {
        Flight exactTwoHours = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(4), now.plusHours(5)); // 2 часа между сегментами

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(exactTwoHours),
                new ExcessiveGroundTimeFilter());

        assertTrue(filtered.isEmpty(),
                "Перелёты с ровно 2 часами на земле должны проходить фильтр");
    }
}