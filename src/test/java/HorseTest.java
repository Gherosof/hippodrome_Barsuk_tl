import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
//    private Horse horse;

//    @BeforeEach
//    public void init() {
//        horse = new Horse("Name Of Horse", 23.3, 14.6);
//    }

    @Test
    @DisplayName("Constructor null 1st param throws IllegalArgumentException with message")
    public void constructorParamIfNullException () {
        Throwable testException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 12.2, 9.2);
        });
        assertEquals(IllegalArgumentException.class.getName(), testException.getClass().getName());
        assertEquals("Name cannot be null.", testException.getMessage());
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
        name,   speed,  distance
        '\t',    12.2,   9.2
        '\r',    12.2,   9.2
        '\s',    12.2,   9.2
        '\f',    12.2,   9.2
        '\n',    12.2,   9.2
    """)
    @DisplayName("Constructor isBlank 1st param throws IllegalArgumentException with message")
    public void constructorIsBlankException(String name, double speed, double distance) {
        assertTrue(name.isBlank());

        Throwable testException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });

        assertEquals(IllegalArgumentException.class.getName(), testException.getClass().getName());
        assertEquals("Name cannot be blank.", testException.getMessage());
    }

    @Test
    @DisplayName("Constructor negative 2nd param throws IllegalArgumentException with message")
    public void constructorIfNegativeSpeedException() {
        Throwable testException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", -12.2, 9.2);
        });
        assertEquals(IllegalArgumentException.class.getName(), testException.getClass().getName());
        assertEquals("Speed cannot be negative.", testException.getMessage());
    }

    @Test
    @DisplayName("Constructor negative 3rd param throws IllegalArgumentException with message")
    public void constructorIfNegativeDistanceException() {
        Throwable testException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Name", 12.2, -9.2);
        });
        assertEquals(IllegalArgumentException.class.getName(), testException.getClass().getName());
        assertEquals("Distance cannot be negative.", testException.getMessage());
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
        name,          speed,  distance
        Horse Name,    12.2,   9.2
    """)
    @DisplayName("getName() повертає рядок, який передавався першим параметром до конструктора")
    public void nameParamInGetName(String name, double speed, double distance) {
        assertEquals(name, new Horse(name, speed, distance).getName());
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
        name,          speed,  distance
        Horse Name,    12.2,   9.2
    """)
    @DisplayName("getSpeed() повертає число, яке передалося другим параметром до конструктора")
    public void speedParamInGetSpeed(String name, double speed, double distance) {
        assertEquals(speed, new Horse(name, speed, distance).getSpeed());
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
        name,          speed,  distance
        Horse Name,    12.2,   9.2
    """)
    @DisplayName("getDistance() повертає число, яке передалося третім параметром до конструктора або нуль, якщо конструктор з двома параметрами")
    public void distanceParamInGetDistance(String name, double speed, double distance) {
        assertEquals(distance, new Horse(name, speed, distance).getDistance());
        assertEquals(0, new Horse(name, speed).getDistance());
    }

    @Test
    @DisplayName("move() викликає всередині метод getRandomDouble")
    public void moveMethodHowWorks() {
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("Test Horse", 1.1, 2.0);
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

    @ParameterizedTest
    @CsvSource({"1.3, 2.0"})
    @DisplayName("getRandomDouble correctly counts distance")
    public void getRandomCorrectlyCountsDistance(double speed, double distance) {
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)){
            double imitation = 2.5;
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(imitation);

            Horse horse = new Horse("Test Horse", speed, distance);
            horse.move();

            assertEquals(distance + speed * imitation, horse.getDistance());
        }
    }
}
