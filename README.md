# Система фильтрации авиаперелётов

## Описание

Система для фильтрации перелётов по различным критериям. Реализована на Java 8+.

## Основные компоненты

### Модели данных

* *Flight* - представляет полёт, состоящий из одного или нескольких сегментов
* *Segment* - отдельный участок перелёта с временем вылета и прилёта

### Фильтры

1. *DepartureBeforeCurrentTimeFilter* - исключает перелёты с вылетом в прошлом
2. *ArrivalBeforeDepartureFilter* - исключает перелёты с некорректными сегментами (прилёт раньше вылета)
3. *ExcessiveGroundTimeFilter* - исключает перелёты с общим временем на земле >2 часов

### Утилиты

* *FlightBuilder* - фабрика для создания демо-данных (продакшн-код)
* *TestFlightBuilder* - фабрика для тестовых данных (тест-код)
* *FlightFilterProcessor* - применяет фильтры к спискам перелётов

## Требования

* Java 8+
* Maven/Gradle (для сборки)
* JUnit 5 (для тестов)

## Использование

1. Получить список перелётов:
```java
List<Flight> flights = FlightBuilder.createFlights();
```
2. Применить фильтр:
```java
List<Flight> filtered = FlightFilterProcessor.filterFlights(
    flights,
    new DepartureBeforeCurrentTimeFilter()
);
```
3. Вывести результаты:
```java
System.out.println("Отфильтрованные перелёты:");
filtered.forEach(System.out::println);
```

## Примеры фильтрации
1. Исключить перелёты с вылетом в прошлом
```java
List<Flight> filtered = FlightFilterProcessor.filterFlights(
    flights,
    new DepartureBeforeCurrentTimeFilter()
);
```
2. Исключить перелёты с некорректными сегментами
```java
List<Flight> filtered = FlightFilterProcessor.filterFlights(
    flights,
    new ArrivalBeforeDepartureFilter()
);
```
3. Исключить перелёты с долгим временем на земле
```java
List<Flight> filtered = FlightFilterProcessor.filterFlights(
    flights,
    new ExcessiveGroundTimeFilter()
);
```

## Запуск тестов
```bash
  mvn test
```
Или через IDE: запустите тесты в 
```java
src/test/java/com/gridnine/testing/
```

## Архитектура
1. Гибкая фильтрация:
   - Использует интерфейс FlightFilter
   - Легко добавлять новые фильтры
2. Тестируемость:
   - Отдельный TestFlightBuilder для тестов
   - Покрытие крайних случаев
3. Производительность:
   - Использует Stream API
   - Оптимизированные алгоритмы фильтрации

## Планы по развитию
1. Новые фильтры:
    - По авиакомпании
    - По общей продолжительности 
    - По количеству пересадок
2. Улучшение тестового покрытия
3. Добавление логирования
4. Поддержка разных источников данных