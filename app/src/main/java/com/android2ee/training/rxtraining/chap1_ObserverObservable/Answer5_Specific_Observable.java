package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer5_Specific_Observable {

    /**
     * Disposable is a core concept for all Observables/Observers
     * It will provide you a way to stop the Observable for the observer side
     *
     * @return An observable that emits integer, every second, until is disposed
     */
    public static Observable<Long> getObservableToDispose() {
        //using the method Interval
        return Observable.interval(1, TimeUnit.SECONDS);
    }

    /**
     * Using Single to emit only one event and then close calling onComplete
     *
     * @return An observable that an item and stop
     */
    public static Single<Long> getSingle() {
        return Single.just(1L);
    }

    /**
     * Using MayBe you will emit or not only one event and then close emitting onComplete
     *
     * @return An observable that emits or not an item
     */
    public static Maybe<Long> getMayBeEmpty() {
        return Maybe.empty();
    }

    /**
     * Using MayBe you will emit or not only one event and then close emitting onComplete
     *
     * @return An observable that emits or not an item
     */
    public static Maybe<Long> getMayBeWithOneItem() {
        return Maybe.just(1L);
    }

    /**
     * Using Completable to emit only the onComplete event
     *
     * @return An observable that only emit onComplete
     */
    public static Completable getCompletable() {
//        return Completable.complete();//work just fine
        return Completable.fromRunnable(() -> longOperation());
    }

    private static void longOperation() {
        System.out.println(" a Long operation Happened here in " + Thread.currentThread().getName());
    }
}
