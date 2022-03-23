package ru.netology.i18n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTests {

    LocalizationService sut;

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of(Country.USA), Arguments.of(Country.GERMANY), Arguments.of(Country.BRAZIL));
    }

    @BeforeAll
    public static void startedAll() {
        System.out.println("Тесты запушены");
    }

    @BeforeEach
    public void started() {
        sut = new LocalizationServiceImpl();
        System.out.println("Тест запушен");
    }

    @AfterEach
    public void finished() {
        System.out.println("Тест выполнен");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("Тесты выполнены");
    }

    @Test
    void testLocaleRussianText() {
        String expected = "Добро пожаловать";
        String preferences = sut.locale(Country.RUSSIA);
        Assertions.assertEquals(expected, preferences);
    }

    @ParameterizedTest
    @MethodSource("source")
    void testLocaleEnglishText(Country country) {
        String expected = "Welcome";
        String preferences = sut.locale(country);
        Assertions.assertEquals(expected, preferences);
    }
}