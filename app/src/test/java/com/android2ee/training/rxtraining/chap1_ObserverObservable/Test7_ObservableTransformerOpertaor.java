package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test7_ObservableTransformerOpertaor {
    private static final String TAG = "ObserverObservableTest";

    public String[] daysOfTheWeek = {"Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"};

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * OutPut result:
     * value is ------.-...--.--
     * value is -..-....-...--.--
     * value is .--.-..-.....-...--.--
     * value is -......-.-....-...--.--
     * value is ..-..-...-...--.--
     * value is ....--..-.-.-...--.--
     * value is .....--.-...--.--
     * OnComplete is called
     */
    @Test
    public void testMap() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("------.-...--.--",
                "-..-....-...--.--", ".--.-..-.....-...--.--",
                "-......-.-....-...--.--", "..-..-...-...--.--",
                "....--..-.-.-...--.--", ".....--.-...--.--");
        Answer7_ObservableTransformerOpertaor.getObservableMap()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is Days of the week are:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testStartWith() {
        List<String> receivedItems = new ArrayList(8);
        String[] results = {"Days of the week are:", "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"};
        List<String> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableStartWith()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }

    private void assertListEquals(List<String> receivedItems, List<String> expectedItems) {
        for (int i = 0; i < expectedItems.size(); i++) {
            Assert.assertEquals(expectedItems.get(i), receivedItems.get(i));
        }
    }

    /**
     * OutPut result:
     * value is Monday
     * OnComplete is called
     * value is empty
     * OnComplete is called
     */
    @Test
    public void testDefaultIfEmpty() {
        Answer7_ObservableTransformerOpertaor.getObservableDefaultIfEmpty("M")
                .subscribe(value -> {
                            System.out.println("value is " + value);

                            Assert.assertEquals("Monday", value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));
        Answer7_ObservableTransformerOpertaor.getObservableDefaultIfEmpty("L")
                .subscribe(value -> {
                            System.out.println("value is " + value);

                            Assert.assertEquals("empty", value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));
    }


    /**
     * OutPut result:
     * value is Monday
     * OnComplete is called
     * value is LMonday
     * value is LTuesday
     * value is LWednesday
     * value is LThursday
     * value is LFriday
     * value is LSaturday
     * value is LSunday
     * OnComplete is called
     */
    @Test
    public void testSwitchIfEmpty() {
        Answer7_ObservableTransformerOpertaor.getObservableSwitchIfEmpty("M")
                .subscribe(value -> {
                            System.out.println("value is " + value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));
        Answer7_ObservableTransformerOpertaor.getObservableSwitchIfEmpty("L")
                .subscribe(value -> {
                            System.out.println("value is " + value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));
    }


    /**
     * OutPut result Sorted:
     * value is Friday
     * value is Monday
     * value is Saturday
     * value is Sunday
     * value is Thursday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     * OutPut result SortedReverse:
     * value is Wednesday
     * value is Tuesday
     * value is Thursday
     * value is Sunday
     * value is Saturday
     * value is Monday
     * value is Friday
     * OnComplete is called
     * OutPut result SortedByLength:
     * value is Monday
     * value is Friday
     * value is Sunday
     * value is Tuesday
     * value is Thursday
     * value is Saturday
     * value is Wednesday
     * OnComplete is called
     */
    @Test
    public void testSorted() {
        String[] resultsSorted = {
                "Friday",
                "Monday",
                "Saturday",
                "Sunday",
                "Thursday",
                "Tuesday",
                "Wednesday",
        };
        String[] resultsSortedReverse = {
                "Wednesday",
                "Tuesday",
                "Thursday",
                "Sunday",
                "Saturday",
                "Monday",
                "Friday",
        };
        String[] resultsSortedByLength = {
                "Monday",
                "Friday",
                "Sunday",
                "Tuesday",
                "Thursday",
                "Saturday",
                "Wednesday",
        };

        List<String> receivedItems = new ArrayList(7);
        /***********************************************************
         *  Test Sorted
         **********************************************************/
        Answer7_ObservableTransformerOpertaor.getObservableSorted()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, Arrays.asList(resultsSorted));
        receivedItems.clear();
        /***********************************************************
         *  Test SortedReverse
         **********************************************************/
        Answer7_ObservableTransformerOpertaor.getObservableSortedReverse()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, Arrays.asList(resultsSortedReverse));
        receivedItems.clear();

        /***********************************************************
         *  Test SortedByLength
         **********************************************************/
        Answer7_ObservableTransformerOpertaor.getObservableSortedByLength()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, Arrays.asList(resultsSortedByLength));
    }

}