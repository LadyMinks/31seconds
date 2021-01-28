package nl.ladyminks.thirtyoneseconds.logic;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author Minka Firth
 */
public class RandomMockTest {

    @Test
    public void randomMockNextInt_ArrayWithValues_RightOrder() {
        final Random random = RandomMock.thatReturns(2, 3, 4, 1);
        assertEquals(2, random.nextInt(42));
        assertEquals(3, random.nextInt(42));
        assertEquals(4, random.nextInt(42));
        assertEquals(1, random.nextInt(42));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void randomMockNextInt_CallNextIntTooOften_MustThrowException() {
        final Random random = RandomMock.thatReturns(2, 3);
        random.nextInt(42);
        random.nextInt(42);
        random.nextInt(42);
    }
}
