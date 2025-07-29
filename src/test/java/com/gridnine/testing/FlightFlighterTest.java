package com.gridnine.testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FlightFilterTest {
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void testDepartureBeforeCurrentTimeFilter() {
        Flight pastFlight = TestFlightBuilder.createTestFlight(now.minusDays(1), now.plusHours(2));
        Flight futureFlight = TestFlightBuilder.createTestFlight(now.plusDays(1), now.plusDays(2));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(pastFlight, futureFlight),
                new DepartureBeforeCurrentTimeFilter());

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(pastFlight));
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        Flight normalFlight = TestFlightBuilder.createTestFlight(now.plusHours(1), now.plusHours(2));
        Flight invalidFlight = TestFlightBuilder.createTestFlight(now.plusHours(1), now.minusHours(1));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(normalFlight, invalidFlight),
                new ArrivalBeforeDepartureFilter());

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(invalidFlight));
    }

    @Test
    void testExcessiveGroundTimeFilter() {
        Flight acceptableFlight = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(4));
        Flight excessiveFlight = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(5), now.plusHours(6));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(acceptableFlight, excessiveFlight),
                new ExcessiveGroundTimeFilter());

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(excessiveFlight));
    }

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

    @Test
    void testSingleSegmentFlight() {
        Flight singleSegment = TestFlightBuilder.createTestFlight(now.plusHours(1), now.plusHours(2));

        // Для перелёта с одним сегментом фильтр времени на земле должен вернуть пустой список
        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(singleSegment), new ExcessiveGroundTimeFilter());

        assertTrue(filtered.isEmpty());
    }

    @Test
    void testExactTwoHoursGroundTime() {
        // Перелёт с точно 2 часами на земле (не должен фильтроваться)
        Flight exactTwoHours = TestFlightBuilder.createTestFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(4), now.plusHours(5));

        List<Flight> filtered = FlightFilterProcessor.filterFlights(
                List.of(exactTwoHours), new ExcessiveGroundTimeFilter());

        assertTrue(filtered.isEmpty());
    }
}