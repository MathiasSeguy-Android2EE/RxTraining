package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer4_Observable_Creators {

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
        return Observable.interval(1, TimeUnit.SECONDS);
    }

    /**
     * @return An observable that emits nothing and fire onComplete
     */
    public static Observable<String> getEmpty() {
        //using the method Empty
        return Observable.empty();
    }

    /**
     * @return An observable that emits nothing and never fire onComplete
     */
    public static Observable<String> getNever() {
        //using the method Never
        return Observable.never();
    }

    /**
     * @return An observable that emits only one Error and close
     */
    public static Observable<String> getError() {
        //using the method Error
        return Observable.error(new IllegalAccessError("You are not allowed"));
    }


    public static String text = "toto";
    public static int timeInterval = 1;

    /**
     * do not create the Observable until the observer subscribes,
     * and create a fresh Observable for each observer
     *
     * @return An observable that can update its internal state
     */
    public static Observable<String> getDefer() {
        //Create a ticker that emits every timeInterval second:
        Observable<String> observable2 = Observable
                .interval(timeInterval, TimeUnit.SECONDS)
                .map(integer -> {
                    return "Obs " + Answer4_Observable_Creators.text + " integer";
                });
        return observable2;
        //It works with every creator you already know
//        return Observable.range(0, timeInterval*4)
//                .map(integer -> "Obs " + Answer4_Observable_Creators.text + " integer");
    }

}
