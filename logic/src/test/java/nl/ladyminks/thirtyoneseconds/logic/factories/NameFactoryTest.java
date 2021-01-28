package nl.ladyminks.thirtyoneseconds.logic.factories;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.ladyminks.thirtyoneseconds.logic.RandomMock;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test for Logic Class NameGenerator.
 */
public class NameFactoryTest {

    @Test
    public void generateName_OneWordInEachList_MustReturnThoseWords() {

        final List<String> listA = new ArrayList<>();
        listA.add("A1");
        final List<String> listB = new ArrayList<>();
        listB.add("B1");
        final List<String> listC = new ArrayList<>();
        listC.add("C1");

        final nl.ladyminks.thirtyoneseconds.logic.factories.NameFactory nameFactory = new nl.ladyminks.thirtyoneseconds.logic.factories.NameFactory(new Random(), listA, listB, listC);
        String expected = "A1 B1-C1";
        String actual = nameFactory.generateName();
        assertEquals(expected, actual);
    }

    @Test
    public void generateName_MultipleWordsInEachList_MustReturnCertainName(){

        final List<String> listA = new ArrayList<>();
        listA.add("A1");
        listA.add("A2");
        listA.add("A3");
        final List<String> listB = new ArrayList<>();
        listB.add("B1");
        listB.add("B2");
        listB.add("B3");
        final List<String> listC = new ArrayList<>();
        listC.add("C1");
        listC.add("C2");
        listC.add("C3");
        nl.ladyminks.thirtyoneseconds.logic.factories.NameFactory nameFactory = new NameFactory(RandomMock.thatReturns(1, 0, 2), listA, listB, listC);

        String expected = "A2 B1-C3";
        String actual = nameFactory.generateName();
        assertEquals(expected, actual);

    }
}