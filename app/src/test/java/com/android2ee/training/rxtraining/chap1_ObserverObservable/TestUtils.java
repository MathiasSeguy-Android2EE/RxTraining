package com.android2ee.training.rxtraining.chap1_ObserverObservable;
// 

import org.junit.Assert;

import java.util.List;

/**
 * Created by Mathias Seguy also known as Android2ee on 15/05/2020.
 * The goal of this class is to :
 */
public class TestUtils {

    public static final String[] daysOfTheWeek = {"Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"};

    public static final String[] daysOfTheWeekSortedByLength = {
            "Monday",
            "Friday",
            "Sunday",
            "Tuesday",
            "Thursday",
            "Saturday",
            "Wednesday",
    };
    public static final String[] resultsSorted = {
            "Friday",
            "Monday",
            "Saturday",
            "Sunday",
            "Thursday",
            "Tuesday",
            "Wednesday",
    };
    public static final String[] resultsSortedReverse = {
            "Wednesday",
            "Tuesday",
            "Thursday",
            "Sunday",
            "Saturday",
            "Monday",
            "Friday",
    };

    public static final void assertListEquals(List<String> receivedItems, List<String> expectedItems) {
        for (int i = 0; i < expectedItems.size(); i++) {
            Assert.assertEquals(expectedItems.get(i), receivedItems.get(i));
        }
    }

    public static final void assertIntegerListEquals(List<Integer> receivedItems, List<Integer> expectedItems) {
        for (int i = 0; i < expectedItems.size(); i++) {
            Assert.assertEquals(expectedItems.get(i), receivedItems.get(i));
        }
    }

    public static final void assertLongListEquals(List<Long> receivedItems, List<Long> expectedItems) {
        for (int i = 0; i < expectedItems.size(); i++) {
            Assert.assertEquals(expectedItems.get(i), receivedItems.get(i));
        }
    }
}
