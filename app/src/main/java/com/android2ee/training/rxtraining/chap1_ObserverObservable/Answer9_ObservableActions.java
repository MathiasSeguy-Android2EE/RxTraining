package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer9_ObservableActions {
    private static final Observable<String> observableSrc = Observable.just(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    );
    private static final Observable<String> observableWithErrorSrc = Observable.create(
            emiter -> {
                emiter.onNext("Monday");
                emiter.onNext(null);
            }
    );
    private static final Observable<Long> observableIntervalSrc = Observable.interval(1, TimeUnit.SECONDS);

    /**
     * Using the observableSrc we use doOnEach to understand its behavior
     *
     * @return
     */
    public static Observable<String> getObservableDoOnEach() {
        /**Notifications represents the reactive signal types: onNext, onError and onComplete and
         * holds their parameter values (a value, a Throwable, nothing).
         * @param <T> the value type*/
        return observableSrc.doOnEach(it ->
        {
            System.out.print("onEach is " + it + " :");
            if (it.isOnNext()) {
                System.out.println("isOnNext = " + it.isOnNext() + " and getValue = " + it.getValue());
            } else if (it.isOnError()) {
                System.out.println("isOnError = " + it.isOnNext() + " and getError = " + it.getError());
            } else {
                System.out.println("isOnComplete = " + it.isOnComplete() + " no value associated");
            }
        });
    }

    /**
     * Using the observableSrc we use doOnNext to understand its behavior
     * Using doOnNext print the length of the emitted day
     *
     * @return
     */
    public static Observable<String> getObservableDoOnNext() {
        /**
         * Only trigger the onNext
         * Only have the value of the next item
         */
        return observableSrc
                .doOnNext(it -> System.out.println("doOnNext Size of " + it + " is " + it.length()));
    }

    /**
     * Using the observableSrc we use doOnNext to understand its behavior
     * Using doOnNext print the length of the emitted day
     *
     * @return
     */
    public static Observable<String> getObservableDoOnLifeCycle() {
        return observableSrc
                .doOnLifecycle(
                        disposable -> System.out.println("0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("1:doOnDispose"));//the action to do in onDispose
    }

    /**
     * Using the observableSrc we use doOnNext to understand its behavior
     * Using doOnNext print the length of the emitted day
     *
     * @return
     */
    public static Observable<Long> getObservableDoOnLifeCycleWithInterval() {
        return observableIntervalSrc
                .doOnLifecycle(
                        disposable -> System.out.println("0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("1:doOnDispose"));//the action to do in onDispose
    }

    /**
     * Using the observableSrc
     * use doOnSubscribe, doOnLifecycle, doOnComplete, doOnDispose, doOnTerminate,
     * doAfterTerminate, doFinally, doAfterNext, doOnNext
     * to understand its behavior
     * This case is the onComplete reached case     *
     *
     * @return
     */
    public static Observable<String> getObservableLifecycleTrackerDoOn() {
        return observableSrc
                .doOnSubscribe(disp -> System.out.println("doOnSubscribe:called with " + disp))
                .doOnLifecycle(
                        disposable -> System.out.println("doOnLifecycle0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("doOnLifecycle1:doOnDispose"))//the action to do in onDispose
                .doOnComplete(() -> System.out.println("doOnComplete:called"))
                .doOnDispose(() -> System.out.println("doOnDispose:called"))
                .doOnTerminate(() -> System.out.println("doOnTerminate:called"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate:called"))
                .doFinally(() -> System.out.println("doFinally:called"))
                .doAfterNext(it -> System.out.println("doAfterNext:called with " + it))
                .doOnNext(it -> System.out.println("doOnNext:called with " + it))
                ;
    }


    /**
     * Using the observableIntervalSrc
     * use doOnSubscribe, doOnLifecycle, doOnComplete, doOnDispose, doOnTerminate,
     * doAfterTerminate, doFinally, doAfterNext, doOnNext
     * to understand its behavior
     * This case is the onDispose reached case     *
     *
     * @return
     */
    public static Observable<Long> getObservableLifecycleTrackerDoOnWithInterval() {
        return observableIntervalSrc
                .doOnSubscribe(disp -> System.out.println("doOnSubscribe:called with " + disp))
                .doOnLifecycle(
                        disposable -> System.out.println("doOnLifecycle0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("doOnLifecycle1:doOnDispose"))//the action to do in onDispose
                .doOnComplete(() -> System.out.println("doOnComplete:called"))
                .doOnDispose(() -> System.out.println("doOnDispose:called"))
                .doOnTerminate(() -> System.out.println("doOnTerminate:called"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate:called"))
                .doFinally(() -> System.out.println("doFinally:called"))
                .doAfterNext(it -> System.out.println("doAfterNext:called with " + it))
                .doOnNext(it -> System.out.println("doOnNext:called with " + it))
                ;
    }

    /**
     * Using the observableWithErrorSrc
     * use doOnSubscribe, doOnLifecycle, doOnComplete, doOnDispose, doOnTerminate,
     * doAfterTerminate, doFinally, doAfterNext, doOnNext
     * to understand its behavior
     * This case is the onDispose reached case     *
     *
     * @return
     */
    public static Observable<String> getObservableLifecycleTrackerWithError() {
        return observableWithErrorSrc
                .doOnSubscribe(disp -> System.out.println("doOnSubscribe:called with " + disp))
                .doOnLifecycle(
                        disposable -> System.out.println("doOnLifecycle0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("doOnLifecycle1:doOnDispose"))//the action to do in onDispose
                .doOnComplete(() -> System.out.println("doOnComplete:called"))
                .doOnDispose(() -> System.out.println("doOnDispose:called"))
                .doOnTerminate(() -> System.out.println("doOnTerminate:called"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate:called"))
                .doFinally(() -> System.out.println("doFinally:called"))
                .doAfterNext(it -> System.out.println("doAfterNext:called with " + it))
                .doOnNext(it -> System.out.println("doOnNext:called with " + it))
                .doOnError(th -> System.out.println("doOnError:called with " + th.getClass().getSimpleName()))
                ;
    }

    /**
     * Using the observableWithErrorSrc skip error and return twice "Buggy Day"
     *
     * @return
     */
    public static Observable<String> getObservableWithOnErrorResumeNext() {
        return observableWithErrorSrc
                .onErrorResumeNext(Observable.just("Buggy day").repeat(2));
    }

    /**
     * Using the observableWithErrorSrc skip error items and return an empty observable
     *
     * @return
     */
    public static Observable<String> getObservableWithOnErrorResumeNextEmpty() {
        //you can also return an empty Observable and then quit the Observable nicely
        return observableWithErrorSrc
                .onErrorResumeNext(Observable.empty());
    }

    /**
     * Using the observableWithErrorSrc skip error items and return a bad day item instead of the error
     *
     * @return
     */
    public static Observable<String> getObservableWithOnErrorReturnItem() {
        return observableWithErrorSrc
                .onErrorReturnItem("Bad day");
    }

    /**
     * Using the observableWithErrorSrc skip error items and return a bad day item instead of the error
     *
     * @return
     */
    public static Observable<String> getObservableWithOnErrorReturn() {
        return observableWithErrorSrc
                .onErrorReturn(th -> "Bad day");
    }

    /**
     * Using the observableWithErrorSrc skip error items and retry twice
     *
     * @return
     */
    public static Observable<String> getObservableWithOnErrorRetry() {
        return observableWithErrorSrc
                .retry(2);
    }

    /**
     * Using the observableWithErrorSrc skip error items and return a bad day item instead of the error
     *
     * @return
     */
    public static Observable<Integer> getObservableWithOnErrorArithm() {
        return observableSrc
                .map(it -> 1 / (7 - it.length()))
                .onErrorReturn(th -> Integer.valueOf(Integer.MAX_VALUE));
    }

    /**
     * Using the observableWithErrorSrc skip error items and return a bad day item instead of the error
     *
     * @return
     */
    public static Observable<Float> getObservableWithOnErrorArithmRecovering() {
        return observableSrc
                .map(it -> {
                    System.out.println("value emitted is " + it + " and lenght=" + it.length());
                    if (it.length() != 7) {
                        return 1f / (7 - it.length());
                    } else {
                        return Float.valueOf(Integer.MAX_VALUE);
                    }
                });
    }
}
