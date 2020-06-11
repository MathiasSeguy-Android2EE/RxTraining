package com.android2ee.training.rxtraining.chap1_ObserverObservable;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 15/05/2020.
 * The goal of this class is to :
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.assertListEquals;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.daysOfTheWeek;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.daysOfTheWeekSortedByLength;
//

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test8_ObservableCollectionOpertaor {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * OutPut result:
     * value is [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday]
     */
    @Test
    public void testToList() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeek);
        Answer8_ObservableCollectionOpertaor.getObservableToList()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            assertListEquals(expectedItems, value);
                        },
                        Throwable::printStackTrace);
    }

    /**
     * OutPut result:
     * value is [Monday, Friday, Sunday, Tuesday, Thursday, Saturday, Wednesday]
     */
    @Test
    public void testToSortedList() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeekSortedByLength);
        Answer8_ObservableCollectionOpertaor.getObservableToSortedList()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            assertListEquals(expectedItems, value);
                        },
                        Throwable::printStackTrace);
    }

    /**
     * OutPut result:
     * value is {6=Sunday, 7=Tuesday, 8=Saturday, 9=Wednesday}
     */
    @Test
    public void testToMap() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeekSortedByLength);
        Answer8_ObservableCollectionOpertaor.getObservableToMap()
                .subscribe(value -> {
                            System.out.println("value is " + value);
//                            assertListEquals( expectedItems,value);
                        },
                        Throwable::printStackTrace);
    }


    /**
     * OutPut result:
     * value is {6=Sun, 7=Tue, 8=Sat, 9=Wed}
     */
    @Test
    public void testMapWithKeyValue() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeekSortedByLength);
        Answer8_ObservableCollectionOpertaor.getObservableToMapWithKeyValue()
                .subscribe(value -> {
                            System.out.println("value is " + value);
//                            assertListEquals( expectedItems,value);
                        },
                        Throwable::printStackTrace);
    }


    /**
     * OutPut result:
     * value is {6=[Mon, Fri, Sun], 7=[Tue], 8=[Thu, Sat], 9=[Wed]}
     */
    @Test
    public void testMultiMapWithKeyValue() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeekSortedByLength);
        Answer8_ObservableCollectionOpertaor.getObservableToMultiMapWithKeyValue()
                .subscribe(value -> {
                            System.out.println("value is " + value);
//                            assertListEquals( expectedItems,value);
                        },
                        Throwable::printStackTrace);
    }


    /**
     * OutPut result:
     * value is [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday]
     */
    @Test
    public void testCollect() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeekSortedByLength);
        ArrayList<String> resultList = new ArrayList<>(7);
        Answer8_ObservableCollectionOpertaor.getObservableCollect(resultList)
                .subscribe(value -> {
                            System.out.println("value is " + value);
//                            assertListEquals( expectedItems,value);
                        },
                        Throwable::printStackTrace);
        assertListEquals(resultList, expectedItems);
    }

    /**
     * OutPut result:
     * value is {6=[Monday, Friday, Sunday], 7=[Tuesday], 8=[Thursday, Saturday], 9=[Wednesday]}
     */
    @Test
    public void testCollectAsMultiMap() {
        List<String> expectedItems = Arrays.asList(daysOfTheWeekSortedByLength);
        Answer8_ObservableCollectionOpertaor.getObservableCollectAsMultiMap()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                        },
                        Throwable::printStackTrace);
    }
}