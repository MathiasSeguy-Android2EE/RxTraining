package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import android.os.Build;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer7_ObservableTransformerOpertaor {
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
        return observableSrc.map(item -> item.toLowerCase().toCharArray())
                .map(chars -> {
                    StringBuffer strBuf = new StringBuffer();
                    for (char aChar : chars) {
                        strBuf.append(morse.get(Character.toString(aChar)));
                    }
                    return strBuf.toString();
                });
    }

    /**
     * Using the observableSrc return a stream that starts with "Days of the week are: "
     */
    public static Observable<String> getObservableStartWith() {
        return observableSrc.startWith("Days of the week are: ");
    }

    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found return "empty"
     */
    public static Observable<String> getObservableDefaultIfEmpty(String firstLetter) {
        return observableSrc.filter(item -> item.toLowerCase().charAt(0) == firstLetter.toLowerCase().charAt(0))
                .defaultIfEmpty("empty");
    }


    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found  add the firstLetter to the days of the week and return them
     */
    public static Observable<String> getObservableSwitchIfEmpty(String firstLetter) {
        return observableSrc.filter(item -> item.toLowerCase().charAt(0) == firstLetter.toLowerCase().charAt(0))
                .switchIfEmpty(observableSrc.map(item -> firstLetter + item));
    }


    /**
     * Using the observableSrc return a stream ordered by alphabetic order
     */
    public static Observable<String> getObservableSorted() {
        return observableSrc.sorted();
    }

    /**
     * Using the observableSrc return a stream ordered by alphabetic order
     */
    public static Observable<String> getObservableSortedReverse() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return observableSrc.sorted(Comparator.reverseOrder());
        } else {
            return observableSrc.sorted((item1, item2) -> -1 * (item1.compareTo(item2)));
        }
    }


    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found  add the firstLetter to the days of the week and return them
     */
    public static Observable<String> getObservableSortedByLength() {
        return observableSrc.sorted((item1, item2) -> Integer.compare(item1.length(), item2.length()));
    }
}
