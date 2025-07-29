package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterProcessor {
    public static List<Flight> filterFlights(List<Flight> flights, FlightFilter filter) {
        return flights.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }
}