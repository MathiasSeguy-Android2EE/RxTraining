package com.android2ee.training.rxtraining.chap1_ObserverObservable.questions;
//

import com.android2ee.training.rxtraining.chap1_ObserverObservable.Answer4_Observable_Creators;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Question4_Observable_Creators {

    /**
     * @return An observable that emits integer from 0 to 10
     */
    public static Observable<Integer> getRange0_10() {
        //using the method Range
        return Observable.range(0, 10);
    }

    /**
     * @return an Observable that emits a ticker every second
     */
    public static Observable<Long> getInterval() {
        //using the method Interval
        return null;
    }

    /**
     * @return An observable that emits nothing and fire onComplete
     */
    public static Observable<String> getEmpty() {
        //using the method Empty
        return null;
    }

    /**
     * @return An observable that emits nothing and never fire onComplete
     */
    public static Observable<String> getNever() {
        //using the method Never
        return null;
    }

    /**
     * @return An observable that emits only one Error and close
     */
    public static Observable<String> getError() {
        //using the method Error
        return null;
    }

    public static int timeInterval = 1;
    /**
     * @return An observable that can update its internal state
     */
    public static Observable<String> getDefer() {
        //Create a ticker that emits every second
        //Update it according to an inner parameter to tick every 2 seconds for the second observer
        //using the timeInterval parameter
        //The constraint here is you can only call this method once, when you retrieve the Observable for te first time
        //Create a ticker that emits every timeInterval second:
        Observable<String> observable2 = Observable
                .interval(timeInterval, TimeUnit.SECONDS)
                .map(integer -> {
                    return "Obs " + Answer4_Observable_Creators.text + " integer";
                });
        //the solution is on the test side
        return observable2;
    }
}
