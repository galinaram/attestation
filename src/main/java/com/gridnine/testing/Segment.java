package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс, представляющий сегмент перелёта - непрерывный участок пути без посадок.
 * Сегмент характеризуется временем вылета и временем прилёта.
 */
public class Segment {
    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;

    /**
     * Конструктор сегмента
     * @param dep Время вылета (не может быть null)
     * @param arr Время прилёта (не может быть null)
     * @throws NullPointerException Если любое из времён равно null
     */
    public Segment(final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    /**
     * @return Время вылета
     */
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    /**
     * @return Время прилёта
     */
    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    /**
     * @return Строковое представление сегмента в формате "[yyyy-MM-dd'T'HH:mm|yyyy-MM-dd'T'HH:mm]"
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + '|' + arrivalDate.format(fmt) + ']';
    }
}