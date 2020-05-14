package com.android2ee.training.rxtraining.chap1_ObserverObservable.questions;
//

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

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
            "Saturday",
            "Saturday",
            "Sunday",
            "Sunday"
    );
    private static final Observable<Long> observableIntervalSrc = Observable.interval(1, TimeUnit.SECONDS);

    /**
     * Using the observableSrc return elements that start with a "s"
     */
    public static Observable<String> getObservableFiltered() {
        return observableSrc;//todo
    }

    /**
     * Using the observableSrc return the first 3 elements of the list
     */
    public static Observable<String> getObservableTakeElement() {
        return observableSrc;//todo
    }

    /**
     * Using the observableIntervalSrc return the elements emitted during the 3 first seconds
     */
    public static Observable<Long> getObservableTakeDuration() {
        return observableIntervalSrc;//todo
    }

    /**
     * Using the observableIntervalSrc return the first elements until the size of the element is smaller than 7 letters
     */
    public static Observable<Long> getObservableTakeWhile() {
        return observableIntervalSrc;//todo
    }

    /**
     * Using the observableSrc return the elements of the list expect the first 3 ones
     */
    public static Observable<String> getObservableSkipElement() {
        return observableSrc;//todo
    }

    /**
     * Using the observableSrc return the last 3 elements of the list
     */
    public static Observable<String> getObservableSkipLastElement() {
        return observableSrc;//todo
    }

    /**
     * Using the observableSrc return the elements of the list a soon as one elements has more than 7 letters
     */
    public static Observable<String> getObservableSkipWhileElement() {
        return observableSrc;//todo
    }

    /**
     * Using the observableIntervalSrc return the elements emitted after the 3 first seconds
     */
    public static Observable<Long> getObservableSkipDuration() {
        return observableIntervalSrc;//todo
    }

    /**
     * Using the observableSrcDuplicated return the elements of the list with no item's duplication
     */
    public static Observable<String> getObservableDistinct() {
        return observableSrcDuplicated;//todo
    }

    /**
     * Using the observableSrcDuplicated return the elements of the list with no item's duplication
     */
    public static Observable<String> getObservableDistinctUntilChanges() {
        return observableSrcDuplicated;//todo
    }
}
