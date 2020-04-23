package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer1_Observable {

    public static Observable<String> getDaysOfWeekSource() {
        Observable<String> toto = Observable.create(
                emiter -> {
                    emiter.onNext("Monday");
                    emiter.onNext("Tuesday");
                    emiter.onNext("Wednesday");
                    emiter.onNext("Thursday");
                    emiter.onNext("Friday");
                    emiter.onNext("Saturday");
                    emiter.onNext("Sunday");
                }
        );
        return toto;
    }

    public static Observable<String> getDaysOfWeekSourceWithJust() {
        Observable<String> toto = Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        return toto;
    }

    public static Observable<String> getDaysOfWeekSourceWithAsList() {
        List<String> weekDays = Arrays.asList(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        return Observable.fromIterable(weekDays);
    }
}
