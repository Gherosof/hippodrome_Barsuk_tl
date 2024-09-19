import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {


    @Test
    @DisplayName("Constructor parameter isNull test")
    public void constructorParamNullTest() {
        Throwable testException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });

        assertEquals(IllegalArgumentException.class.getName(), testException.getClass().getName());
        assertEquals("Horses cannot be null.", testException.getMessage());
    }

    @Test
    @DisplayName("Constructor parameter isEmpty test")
    public void constructorParamIsEmptyTest() {
        Throwable testException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<Horse>());
        });

        assertEquals(IllegalArgumentException.class.getName(), testException.getClass().getName());
        assertEquals("Horses cannot be empty.", testException.getMessage());
    }

    /*
    метод getHorses
    Перевірити, що метод повертає список, який містить ті ж об'єкти і в такій самій послідовності,
    що й список, який передався до конструктора. При створенні об'єкта Hippodrome передай до
    конструктора список із 30 різних коней;
    */

    @Test
    @DisplayName("getHorses() returned the same list in constructor")
    public void getHorsesListTest() {
        List<Horse> testList = new ArrayList<>();
        int count = 30;
        for (int i = 0; i < count; i++) {
            testList.add(new Horse("" + i, 0.0));
        }
        Hippodrome hippodromeTest = new Hippodrome(testList);
        List<Horse> fromHippodrome = hippodromeTest.getHorses();
        for (int i = 0; i < count; i++) {
            assertEquals(testList.get(i).getName(), fromHippodrome.get(i).getName());
            assertEquals(testList.get(i).getSpeed(), fromHippodrome.get(i).getSpeed());
            assertEquals(testList.get(i).getDistance(), fromHippodrome.get(i).getDistance());
        }
    }

    @Test
    @DisplayName("Hippodrome.move() calls 50 times horse.move()")
    public void hippodromeMoveTest() {

        Horse testHorse = Mockito.mock(Horse.class);
//        List<Horse> testList = new ArrayList<>(50);
//        Collections.fill(testList, testHorse);
        int count = 50;
        List<Horse> testList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            testList.add(testHorse);
        }

        Hippodrome hippodromeTest = new Hippodrome(testList);
        hippodromeTest.move();
        Mockito.verify(testHorse, Mockito.times(50)).move();
    }

    @Test
    @DisplayName("Hippodrome.getWinner() returns max distance of horse")
    public void getWinnerMaxDistanceTest() {
        Horse one = new Horse("One", 1.1, 2.0);
        Horse two = new Horse("Two", 1.1, 2.2);
        List<Horse> testHorses = List.of(one, two);

        Hippodrome hippodrome = new Hippodrome(testHorses);
        assertEquals(two, hippodrome.getWinner());
    }
}
