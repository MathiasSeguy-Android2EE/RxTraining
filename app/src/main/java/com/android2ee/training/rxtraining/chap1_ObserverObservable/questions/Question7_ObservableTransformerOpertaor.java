package com.android2ee.training.rxtraining.chap1_ObserverObservable.questions;
//

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand operators on Observable:
 */
public class Question7_ObservableTransformerOpertaor {

    private static final Observable<String> observableSrc = Observable.just(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    );
    private static Map<String, String> morse = new HashMap<>();

    static {
        morse.put("a", ".-");
        morse.put("b", "-...");
        morse.put("c", "-.-.");
        morse.put("d", "-..");
        morse.put("e", ".");
        morse.put("f", "..-.");
        morse.put("g", "--.-");
        morse.put("h", "....");
        morse.put("i", "..");
        morse.put("j", ".---");
        morse.put("k", "-.-");
        morse.put("l", ".-..");
        morse.put("m", "--");
        morse.put("n", "-.");
        morse.put("o", "---");
        morse.put("p", ".--.");
        morse.put("q", "--.-");
        morse.put("r", ".-.");
        morse.put("s", "...");
        morse.put("t", "-");
        morse.put("u", "..-");
        morse.put("v", "...-");
        morse.put("w", ".--");
        morse.put("x", "-..-");
        morse.put("y", "-.--");
        morse.put("z", "--..");
    }

    private static final Observable<String> observableSrcDuplicated = Observable.fromArray(
            "Monday",
            "Monday",
            "Tuesday",
            "Tuesday",
            "Wednesday",
            "Wednesday",
            "Thursday",
            "Thursday",
            "Friday",
            "Friday",
            "Tuesday",
            "Saturday",
            "Saturday",
            "Sunday",
            "Sunday"
    );
    private static final Observable<Long> observableIntervalSrc = Observable.interval(1, TimeUnit.SECONDS);

    /**
     * Using the observableSrc return the associated morse code
     */
    public static Observable<String> getObservableMap() {
        return observableSrc;//TODO
    }

    /**
     * Using the observableSrc return a stream that starts with "Days of the week are: "
     */
    public static Observable<String> getObservableStartWith() {
        return observableSrc;//TODO
    }

    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found return "empty"
     */
    public static Observable<String> getObservableDefaultIfEmpty(String firstLetter) {
        return observableSrc;//TODO
    }


    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found  add the firstLetter to the days of the week and return them
     */
    public static Observable<String> getObservableSwitchIfEmpty(String firstLetter) {
        return observableSrc;//TODO
    }


    /**
     * Using the observableSrc return a stream ordered by alphabetic order
     */
    public static Observable<String> getObservableSorted() {
        return observableSrc;//TODO
    }

    /**
     * Using the observableSrc return a stream ordered by alphabetic order
     */
    public static Observable<String> getObservableSortedReverse() {
        return observableSrc;//TODO
    }


    /**
     * Using the observableSrc return a stream ordered by words's length
     */
    public static Observable<String> getObservableSortedByLength() {
        return observableSrc;//TODO
    }


    /**
     * Using the observableSrc return a stream ordered by words's length
     */
    public static Observable<String> getObservableDelayed() {
        return observableSrc;//TODO
    }

    /**
     * Using the observableSrc return twice the 3 first days of the week week
     */
    public static Observable<String> getObservableRepeat() {
        return observableSrc;//TODO
    }

    /**
     * Using the observableSrc test scan
     */
    public static Observable<String> getObservableScanStupid() {
        return observableSrc;//TODO
    }


    /**
     * Using the observableSrc return the list of the days of the week split by a ','
     */
    public static Observable<String> getObservableScan() {
        return observableSrc;//TODO
    }


    /**
     * Using the observableSrc return the accumulate length of each days
     */
    public static Observable<Integer> getObservableScanWithInitializer() {
        return null;//TODO
    }


    /**
     * Using the observableSrc return the list of the day split by a coma
     */
    public static Maybe<String> getObservableReduce() {
        return null;//TODO
    }

    /**
     * Using the observableSrc return the accumulate length of each days
     */
    public static Single<Integer> getObservableReduceWithInitializer() {
        return null;//TODO
    }


    /**
     * Using the observableSrc return the number of days in a week
     */
    public static Single<Long> getObservableCount() {
        return null;//TODO
    }


    /**
     * Using the observableSrc return true if all the day are called Wednesday
     */
    public static Single<Boolean> getObservableAll() {
        return null;//TODO
    }


    /**
     * Using the observableSrc return true if there is a day called Wednesday
     */
    public static Single<Boolean> getObservableAny() {
        return null;//TODO
    }


    /**
     * Using the observableSrc return true if the items emitted contains Wednesday
     */
    public static Single<Boolean> getObservableContains() {
        return null;//TODO
    }
}