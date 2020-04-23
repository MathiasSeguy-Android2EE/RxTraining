package com.android2ee.training.rxtraining.chap1_ObserverObservable;
// 

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

/**
 * Created by Mathias Seguy also known as Android2ee on 17/04/2020.
 * The goal of this class is to explain the difference between Cold and Hot:
 * Cold Observables:
 * They will emits the same dataset to each observer when ever this observer register itself
 * It's like a playing a CD
 * Think about it like a data stream. Each observers will have its own stream of data.
 * Ex: Just,  fromIterable (because data set is finite)
 * All the Observables from the prevoious exercices 1, 2 are cold
 * <p>
 * Hot Observer:
 * They will emits a stream who ever is listening.
 * If observers register late, they don't get the previous emitted messages
 * It's like the Radio, you listen what is brodcasted, not what was broadcasted
 * Think about it like a stream of events. Each observers share the stream of events.
 * Ex: create can have an infinite loop that emits in the time and cannot replay the previous emission it has done
 * <p>
 * ConnectableObservables:
 * Help switch from cold to hot by synchrnozing the subscription
 * It will give you the ability to subscribe your observer and when they are all under subscription
 * you can launch the stream
 */
public class Answer3_SeveralSubscribing_ColdVsHot {
    public static Observable<String> getColdObservables() {
        return Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
    }


    private static Boolean lotoIsOpen = Boolean.TRUE;

    /**
     * Create an observable that emits an event every time a random value is above 0.8
     * Make a 1 second pause
     *
     * @return
     */
    public static Observable<Integer> getHotObservable() {
        //return a hot observable that emits the current second every second ten times
        Observable hotObserable = Observable.create(emiter -> {
            lotoIsOpen = Boolean.TRUE;
            long timeInMillis = System.currentTimeMillis();
            int numberOfWinner = 0;
            while (lotoIsOpen) {
                int value = (int) (Math.random() * 1_000);
                if (value > 800) {
                    System.out.println("hotObserable[Thread]=" + Thread.currentThread().getName() + "value is " + value);
                    emiter.onNext(value);
                    numberOfWinner++;
                }
                if (numberOfWinner > 5) {
                    lotoIsOpen = Boolean.FALSE;
                    emiter.onComplete();
                }
                Thread.sleep(1_000);
            }
            emiter.onComplete();
        });
        return hotObserable;
    }

    public static ConnectableObservable<String> getConnectableObservable() {
        return getHotObservable()
                .map(it -> {
                    return "" + it;
                })
                .publish();
    }
}
