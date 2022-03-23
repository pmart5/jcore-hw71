package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.netology.entity.*;
import ru.netology.geo.*;
import ru.netology.i18n.*;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTests {

    MessageSender sut;
    MockitoSession session;
    GeoService geoServiceMock = Mockito.mock(GeoServiceImpl.class);
    LocalizationService localizationServiceMock = Mockito.mock(LocalizationServiceImpl.class);
    Location locationMock = Mockito.mock(Location.class);

    @BeforeAll
    public static void startedAll() {
        System.out.println("Тесты запушены");
    }

    @BeforeEach
    public void started() {
        sut = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
        System.out.println("Тест запушен");
    }

    @AfterEach
    public void finished() {
        session.finishMocking();
        System.out.println();
        System.out.println("Тест выполнен");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("Тесты выполнены");
    }

    @Test
    void testSendRussianText() {
        String ip = "172.1.1.1";
        String expected = "Добро пожаловать";
        Mockito.when(locationMock.getCountry()).thenReturn(Country.RUSSIA);
        Mockito.when(geoServiceMock.byIp("172.1.1.1")).thenReturn(locationMock);
        Mockito.when(localizationServiceMock.locale(locationMock.getCountry())).thenReturn("Добро пожаловать");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String preferences = sut.send(headers);
        Assertions.assertEquals(expected, preferences);
    }

    @Test
    void testSendEnglishText() {
        String ip = "96.1.1.1";
        String expected = "Welcome";
        Mockito.when(locationMock.getCountry()).thenReturn(Country.USA);
        Mockito.when(geoServiceMock.byIp("96.1.1.1")).thenReturn(locationMock);
        Mockito.when(localizationServiceMock.locale(locationMock.getCountry())).thenReturn("Welcome");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String preferences = sut.send(headers);
        Assertions.assertEquals(expected, preferences);
    }

    @Test
    void testSendVerifyCall() {
        Mockito.when(locationMock.getCountry()).thenReturn(Country.RUSSIA);
        Mockito.when(geoServiceMock.byIp(Mockito.anyString())).thenReturn(locationMock);
        Mockito.when(localizationServiceMock.locale(locationMock.getCountry())).thenReturn("Добро пожаловать");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.1.1.1");
        sut.send(headers);
        Mockito.verify(locationMock, Mockito.atLeastOnce()).getCountry();
        Mockito.verify(geoServiceMock, Mockito.times(1)).byIp(Mockito.anyString());
        Mockito.verify(localizationServiceMock, Mockito.atMost(2)).locale(Mockito.<Country>any());
    }
}