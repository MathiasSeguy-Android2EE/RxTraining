package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer6_ObservableOpertaor {
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
            "Tuesday",
            "Saturday",
            "Saturday",
            "Sunday",
            "Sunday"
    );
    private static final Observable<Long> observableIntervalSrc = Observable.interval(1, TimeUnit.SECONDS);

    /**
     * Using the observableSrc return elements that start with a "s" or "S"
     */
    public static Observable<String> getObservableFiltered() {
        return observableSrc.filter(item -> item.toLowerCase().charAt(0) == 's');
    }

    /**
     * Using the observableSrc return the first 3 elements of the list
     */
    public static Observable<String> getObservableTakeElement() {
//        return observableSrc.take(3);//take, takeLast,takeUntil,takeWhile with time or numbers
        return observableSrc.take(3);
    }

    /**
     * Using the observableIntervalSrc return the elements emitted during the 3 first seconds
     */
    public static Observable<Long> getObservableTakeDuration() {
        return observableIntervalSrc.take(3500, TimeUnit.MILLISECONDS);
    }

    /**
     * Using the observableSrc return the first elements until the size of the element is smaller than 7 letters
     */
    public static Observable<String> getObservableTakeUntil() {
        return observableSrc.takeUntil(item -> item.length() > 7);
    }

    /**
     * Using the observableSrc return the first elements until the size of the element is smaller than 7 letters
     */
    public static Observable<String> getObservableTakeWhile() {
        return observableSrc.takeWhile(item -> item.length() < 8);
    }

    /**
     * Using the observableSrc return the last 3 elements of the list
     */
    public static Observable<String> getObservableTakeLastElement() {
        return observableSrc.takeLast(3);
    }

    /**
     * Using the observableSrc return the elements of the list expect the first 3 ones
     */
    public static Observable<String> getObservableSkipElement() {
        return observableSrc.skip(3);
    }

    /**
     * Using the observableSrc return the full list except the last 3 items
     */
    public static Observable<String> getObservableSkipLastElement() {
        return observableSrc.skipLast(3);
    }


    /**
     * Using the observableSrc return the elements of the list a soon as one elements has more than 7 letters
     */
    public static Observable<String> getObservableSkipWhileElement() {
        return observableSrc.skipWhile(item -> item.length() < 8);
    }

    /**
     * Using the observableIntervalSrc return the elements emitted after the 3 first seconds
     */
    public static Observable<Long> getObservableSkipDuration() {
        return observableIntervalSrc.skip(2900, TimeUnit.MILLISECONDS);
    }

    /**
     * Using the observableSrcDuplicated return the elements of the list with no item's duplication
     */
    public static Observable<String> getObservableDistinct() {
        return observableSrcDuplicated.distinct();
    }

    /**
     * Using the observableSrcDuplicated return the elements of the list with no item's duplication
     */
    public static Observable<String> getObservableDistinctUntilChanges() {
        return observableSrcDuplicated.distinctUntilChanged();
    }
}
