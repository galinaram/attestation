package com.gridnine.testing;

/**
 * Функциональный интерфейс для фильтрации перелётов.
 * Реализации этого интерфейса определяют различные правила фильтрации.
 */
@FunctionalInterface
public interface FlightFilter {
    /**
     * Проверяет, удовлетворяет ли перелёт условиям фильтра
     * @param flight Перелёт для проверки
     * @return true если перелёт соответствует условиям фильтра, false в противном случае
     */
    boolean test(Flight flight);
}