package com.android2ee.training.rxtraining.chap1_ObserverObservable;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 15/05/2020.
 * The goal of this class is to :
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.disposables.Disposable;
//

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test9_ObservableAction {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * OutPut result:
     * onEach is OnNextNotification[Monday] :isOnNext = true and getValue = Monday
     * onEach is OnNextNotification[Tuesday] :isOnNext = true and getValue = Tuesday
     * onEach is OnNextNotification[Wednesday] :isOnNext = true and getValue = Wednesday
     * onEach is OnNextNotification[Thursday] :isOnNext = true and getValue = Thursday
     * onEach is OnNextNotification[Friday] :isOnNext = true and getValue = Friday
     * onEach is OnNextNotification[Saturday] :isOnNext = true and getValue = Saturday
     * onEach is OnNextNotification[Sunday] :isOnNext = true and getValue = Sunday
     * onEach is OnCompleteNotification :isOnComplete = true no value associated
     */
    @Test
    public void testDoOnEach() {
        Answer9_ObservableActions.getObservableDoOnEach()
                .subscribe();
    }

    /**
     * OutPut result:
     * doOnNext Size of Monday is 6
     * doOnNext Size of Tuesday is 7
     * doOnNext Size of Wednesday is 9
     * doOnNext Size of Thursday is 8
     * doOnNext Size of Friday is 6
     * doOnNext Size of Saturday is 8
     * doOnNext Size of Sunday is 6
     */
    @Test
    public void testDoOnNext() {
        Answer9_ObservableActions.getObservableDoOnNext()
                .subscribe();
    }


    /**
     * OutPut result:
     * 0:doOnSubscribe
     * value emitted is Monday
     * value emitted is Tuesday
     * value emitted is Wednesday
     * value emitted is Thursday
     * value emitted is Friday
     * value emitted is Saturday
     * value emitted is Sunday
     * onCompleteCalled
     * <p>
     * /!\onDispose is never called if the observer reaches onComplete before being dispose/!\
     */
    @Test
    public void testDoOnLifeCycle() {
        Disposable disposable = Answer9_ObservableActions.getObservableDoOnLifeCycle()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
        disposable.dispose();
    }


    /**
     * OutPut result:
     * 0:doOnSubscribe
     * value emitted is 0
     * value emitted is 1
     * Disposing the observable while it still runs
     * 1:doOnDispose
     */
    @Test
    public void testDoOnLifeCycleWithInterval() throws InterruptedException {
        Disposable disposable = Answer9_ObservableActions.getObservableDoOnLifeCycleWithInterval()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
        Thread.sleep(2500);
        System.out.println("Disposing the observable while it still runs");
        disposable.dispose();
    }


    /**
     * OutPut result:
     * doOnSubscribe:called with io.reactivex.internal.operators.observable.ObservableFromArray$FromArrayDisposable@3c09711b
     * doOnLifecycle0:doOnSubscribe
     * doOnNext:called with Monday
     * value emitted is Monday
     * doAfterNext:called with Monday
     * ... blabla ...
     * doAfterNext:called with Sunday
     * doOnComplete:called <-onComplete called, onDispose not called
     * doOnTerminate:called <-onTerminate called, onDispose not called
     * onCompleteCalled <-onComplete called, onDispose not called
     * doFinally:called <-always called
     * doAfterTerminate:called <-doAfterTerminate called, onDispose not called
     * Disposing the observable while it still runs
     */
    @Test
    public void testObservableLifecycleTrackerDoOn() throws InterruptedException {
        Disposable disposable = Answer9_ObservableActions.getObservableLifecycleTrackerDoOn()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
        Thread.sleep(2500);
        System.out.println("Disposing the observable while it still runs");
        disposable.dispose();
    }


    /**
     * OutPut result:
     * doOnSubscribe:called with null
     * doOnLifecycle0:doOnSubscribe
     * doOnNext:called with 0
     * value emitted is 0
     * doAfterNext:called with 0
     * doOnNext:called with 1
     * value emitted is 1
     * doAfterNext:called with 1
     * Disposing the observable while it still runs
     * doOnDispose:called <-onDispose called, onComplete, onTerminate, doAfterTerminate not called
     * doOnLifecycle1:doOnDispose
     * doFinally:called
     */
    @Test
    public void testObservableLifecycleTrackerDoOnWithInterval() throws InterruptedException {
        Disposable disposable = Answer9_ObservableActions.getObservableLifecycleTrackerDoOnWithInterval()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
        Thread.sleep(2500);
        System.out.println("Disposing the observable while it still runs");
        disposable.dispose();
    }

    /**
     * OutPut result:
     * doOnSubscribe:called with null
     * doOnLifecycle0:doOnSubscribe
     * doOnNext:called with Monday
     * value emitted is Monday
     * doAfterNext:called with Monday
     * doOnTerminate:called
     * doOnError:called with NullPointerException
     * exception thrown java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     * java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     * doFinally:called
     * at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * doAfterTerminate:called
     * at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     */
    @Test
    public void testObservableLifecycleTrackerWithError() {
        Answer9_ObservableActions.getObservableLifecycleTrackerWithError()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        th -> {
                            System.out.println("exception thrown " + th);
                            th.printStackTrace();
                        },
                        () -> System.out.println("onCompleteCalled"));
    }


    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Buggy day
     * value emitted is Buggy day
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorResumeNext() {
        Answer9_ObservableActions.getObservableWithOnErrorResumeNext()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }

    /**
     * When returning an empty Observable, you can quit the process nicely
     * <p>
     * OutPut result:
     * value emitted is Monday
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorResumeNextEmpty() {
        Answer9_ObservableActions.getObservableWithOnErrorResumeNextEmpty()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }


    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Bad day
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorReturnItem() {
        Answer9_ObservableActions.getObservableWithOnErrorReturnItem()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }

    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Bad day
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorReturn() {
        Answer9_ObservableActions.getObservableWithOnErrorReturn()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }

    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Monday
     * value emitted is Monday
     * exception thrown java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     * java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     */
    @Test
    public void testObservableWithOnErrorRecover() {
        Answer9_ObservableActions.getObservableWithOnErrorRetry()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        th -> {
                            System.out.println("exception thrown " + th);
                            th.printStackTrace();
                        },
                        () -> System.out.println("onCompleteCalled"));
    }


    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Monday
     * value emitted is Monday
     * java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     */
    @Test
    public void testObservableWithOnErrorArithm() {
        Answer9_ObservableActions.getObservableWithOnErrorArithm()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }

    /**
     * OutPut result:
     * value emitted is Monday and lenght=6
     * value emitted is 1.0
     * value emitted is Tuesday and lenght=7
     * value emitted is 2.14748365E9
     * value emitted is Wednesday and lenght=9
     * value emitted is -0.5
     * value emitted is Thursday and lenght=8
     * value emitted is -1.0
     * value emitted is Friday and lenght=6
     * value emitted is 1.0
     * value emitted is Saturday and lenght=8
     * value emitted is -1.0
     * value emitted is Sunday and lenght=6
     * value emitted is 1.0
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorArithmRecovering() {
        Answer9_ObservableActions.getObservableWithOnErrorArithmRecovering()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }
}