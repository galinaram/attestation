package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("All flights:");
        flights.forEach(System.out::println);

        System.out.println("\nFlights with departure before current time:");
        FlightFilterProcessor.filterFlights(flights, new DepartureBeforeCurrentTimeFilter())
                .forEach(System.out::println);

        System.out.println("\nFlights with arrival before departure:");
        FlightFilterProcessor.filterFlights(flights, new ArrivalBeforeDepartureFilter())
                .forEach(System.out::println);

        System.out.println("\nFlights with excessive ground time (>2 hours):");
        FlightFilterProcessor.filterFlights(flights, new ExcessiveGroundTimeFilter())
                .forEach(System.out::println);
    }
}