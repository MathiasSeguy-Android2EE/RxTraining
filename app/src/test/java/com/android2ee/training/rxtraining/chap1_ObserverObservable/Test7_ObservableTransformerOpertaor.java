package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.assertIntegerListEquals;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.assertListEquals;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.daysOfTheWeekSortedByLength;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.resultsSorted;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.resultsSortedReverse;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test7_ObservableTransformerOpertaor {
    private static final String TAG = "ObserverObservableTest";

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

        assertListEquals(receivedItems, Arrays.asList(daysOfTheWeekSortedByLength));
    }

    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    @Test
    public void testRepeat() {
        List<String> receivedItems = new ArrayList(8);
        String[] results = {"Monday", "Tuesday", "Wednesday",
                "Monday", "Tuesday", "Wednesday"};
        List<String> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableRepeat()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }


    /**
     * OutPut result:     *
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * OnComplete is called
     */
    @Test
    public void testScanStupid() {
        List<String> receivedItems = new ArrayList(7);
        String[] results = {"Monday", "Monday", "Monday", "Monday",
                "Monday", "Monday", "Monday"};
        List<String> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableScanStupid()
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
     * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
     * OnComplete is called
     */
    @Test
    public void testScan() {
        List<String> receivedItems = new ArrayList(1);
        String[] results = {"Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday"};
        List<String> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableScan()
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
     * value is 0
     * value is 6
     * value is 13
     * value is 22
     * value is 30
     * value is 36
     * value is 44
     * value is 50
     * OnComplete is called
     */
    @Test
    public void testScanWithInitializer() {
        List<Integer> receivedItems = new ArrayList(8);
        Integer[] results = {0, 6, 13, 22, 30, 36, 44, 50};
        List<Integer> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableScanWithInitializer()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertIntegerListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
     * OnComplete is called
     */
    @Test
    public void testReduce() {
        List<String> receivedItems = new ArrayList(1);
        String[] results = {"Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday"};
        List<String> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableReduce()
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
     * value is 50
     * OnComplete is called
     */
    @Test
    public void testReduceWithInitializer() {
        List<Integer> receivedItems = new ArrayList(1);
        Integer[] results = {50};
        List<Integer> expectedItems = Arrays.asList(results);
        Answer7_ObservableTransformerOpertaor.getObservableReduceWithInitializer()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace);

        assertIntegerListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is 50
     * OnComplete is called
     */
    @Test
    public void testCount() {
        Answer7_ObservableTransformerOpertaor.getObservableCount()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            Assert.assertEquals(7L, 0L + value);
                        },
                        Throwable::printStackTrace);
    }


    /**
     * OutPut result:
     * value is False
     * value is True
     * value is True
     * OnComplete is called
     */
    @Test
    public void testAllAnyContains() {
        Answer7_ObservableTransformerOpertaor.getObservableAll()
                .subscribe(value -> Assert.assertFalse(value));
        Answer7_ObservableTransformerOpertaor.getObservableAny()
                .subscribe(value -> Assert.assertTrue(value));
        Answer7_ObservableTransformerOpertaor.getObservableContains()
                .subscribe(value -> Assert.assertTrue(value));
    }
}