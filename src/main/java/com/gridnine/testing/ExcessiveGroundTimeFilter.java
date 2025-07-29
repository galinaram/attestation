package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ExcessiveGroundTimeFilter implements FlightFilter {
    private static final long MAX_GROUND_TIME_HOURS = 2;

    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = flight.getSegments();
        if (segments.size() < 2) return false;

        long totalGroundTime = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime currentArrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
            totalGroundTime += Duration.between(currentArrival, nextDeparture).toHours();
        }

        return totalGroundTime > MAX_GROUND_TIME_HOURS;
    }
}