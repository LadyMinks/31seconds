package nl.ladyminks.thirtyoneseconds.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * An implementation of Random that generates the required int values needed for testing.
 *
 * @author Minka Firth
 */
public class RandomMock extends Random {

    public static RandomMock thatReturns(Integer... values) {
        List<Integer> arrayValues = Arrays.asList(values);
        return new RandomMock(arrayValues);
    }

    private final List<Integer> randomList;
    private int index = 0;

    private RandomMock(List<Integer> randomList) {
        this.randomList = randomList;
    }

    @Override
    public int nextInt(int bound) {
        int returnValue = randomList.get(index);
        index++;
        return returnValue;
    }
}
