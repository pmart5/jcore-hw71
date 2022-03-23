package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.*;

import java.util.stream.Stream;

public class GeoServiceImplTests {

    GeoService sut;

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.2.3", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("172.3.20.100", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.2.3", new Location("New York", Country.USA, null,  0)),
                Arguments.of("96.3.20.100", new Location("New York", Country.USA, null,  0)));
    }

    @BeforeAll
    public static void startedAll() {
        System.out.println("Тесты запушены");
    }

    @BeforeEach
    public void started() {
        sut = new GeoServiceImpl();
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

    @ParameterizedTest
    @MethodSource("source")
    void testByIpLocation(String ip, Location expected) {
        Location preferences = sut.byIp(ip);
        Assertions.assertEquals(expected.getCity(), preferences.getCity());
        Assertions.assertEquals(expected.getCountry(), preferences.getCountry());
        Assertions.assertEquals(expected.getStreet(), preferences.getStreet());
        Assertions.assertEquals(expected.getBuiling(), preferences.getBuiling());
    }

    @Test
    void testByIpNullLocation() {
        String ip = "";
        Location preferences = sut.byIp(ip);
        Assertions.assertNull(preferences);
    }
}