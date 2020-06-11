package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer8_ObservableCollectionOpertaor {
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
     * Using the observableSrc return the list of the days of the week
     */
    public static Single<List<String>> getObservableToList() {
        return observableSrc.toList(7);
    }

    /**
     * Using the observableSrc return the list of the days of the week sorted by name's length
     */
    public static Single<List<String>> getObservableToSortedList() {
        return observableSrc.toSortedList((item1, item2) -> Integer.compare(item1.length(), item2.length()), 7);
    }


    /**
     * Using the observableSrc return the list of the days of the week grouped by name's length
     */
    public static Single<Map<Integer, String>> getObservableToMap() {
        return observableSrc.toMap(item -> item.length()/* This is the map's key of the item*/);
    }

    /**
     * Using the observableSrc return the list of the days of the week grouped by name's length
     */
    public static Single<Map<Integer, String>> getObservableToMapWithKeyValue() {
        return observableSrc.toMap(item -> item.length()/* This is the map's key of the item*/,
                item -> item.substring(0, 3)/* This is the map's value of the item*/);
    }

    /**
     * Using the observableSrc return the list of the days of the week sorted by name's length
     */
    public static Single<Map<Integer, Collection<String>>> getObservableToMultiMapWithKeyValue() {
        return observableSrc.toMultimap(item -> item.length()/* This is the map's key of the item*/,
                item -> item.substring(0, 3)/* This is the map's value of the item*/);
    }


    /**
     * Using the observableSrc and collect return the list myList filled with emitted items
     */
    public static Single<List<String>> getObservableCollect(ArrayList<String> myList) {
        return observableSrc.collect(
                () -> myList,//Constructor
                (list, item) -> list.add((String) item)//way to manage each items
        );
    }


    /**
     * Using the observableSrc and collect return the same map as the one obtain by ToMultiMap
     * Use collect to code toMultiMap
     */
    public static Single<HashMap<Integer, List<String>>> getObservableCollectAsMultiMap() {
        return observableSrc.collect(
                () -> new HashMap<Integer, List<String>>(),//Constructor
                (map, item) -> {
                    List valueList = map.get(item.length());
                    if (valueList == null) {
                        valueList = new ArrayList();
                        valueList.add(item);
                        map.put(item.length(), valueList);
                    } else {
                        valueList.add(item);
                    }
                }//way to manage each items
        );
    }
}
