package com.gridnine.testing;

import java.time.LocalDateTime;

public class DepartureBeforeCurrentTimeFilter implements FlightFilter {
    @Override
    public boolean test(Flight flight) {
        LocalDateTime now = LocalDateTime.now();
        return flight.getSegments().stream()
                .anyMatch(segment -> segment.getDepartureDate().isBefore(now));
    }
}