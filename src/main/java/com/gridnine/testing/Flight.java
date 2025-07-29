package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, представляющий перелёт, состоящий из одного или нескольких сегментов.
 * Перелёт - это путь из точки A в точку B, возможно с промежуточными посадками.
 */
public class Flight {
    private final List<Segment> segments;

    /**
     * Конструктор перелёта
     * @param segments Список сегментов, из которых состоит перелёт
     */
    public Flight(final List<Segment> segments) {
        this.segments = segments;
    }

    /**
     * @return Неизменяемый список сегментов перелёта
     */
    public List<Segment> getSegments() {
        return segments;
    }

    /**
     * @return Строковое представление перелёта в формате "[вылет|прилёт] [вылет|прилёт]..."
     */
    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}