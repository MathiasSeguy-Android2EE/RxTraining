package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import com.android2ee.training.rxtraining.PrintInColorKt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test5_Specific_Observable {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testObservableToDispose() {
        //To obtain a disposable, just use the object returned by subscribe
        Observable<Long> observable = Answer5_Specific_Observable.getObservableToDispose();
        Disposable disposable = observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);
                        },
                        Throwable::printStackTrace,
                        () -> PrintInColorKt.println("OnComplete is never called " + Thread.currentThread().getName(),
                                PrintInColorKt.ANSI_PURPLE));
        PrintInColorKt.println("Interval is not blocking " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
    }

    @Test
    public void testObservableToDisposeAndReuse() {
        //To obtain a disposable, just use the object returned by subscribe
        Observable<Long> observable = Answer5_Specific_Observable.getObservableToDispose();
        Disposable disposable = observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);
                        },
                        Throwable::printStackTrace,
                        () -> PrintInColorKt.println("OnComplete is never called " + Thread.currentThread().getName(),
                                PrintInColorKt.ANSI_PURPLE));
        PrintInColorKt.println("Interval is not blocking " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
        PrintInColorKt.println("Let's try to use the Observable again " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        disposable = observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);
                        },
                        Throwable::printStackTrace,
                        () -> PrintInColorKt.println("OnComplete is never called " + Thread.currentThread().getName(),
                                PrintInColorKt.ANSI_PURPLE));
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
    }


    @Test
    public void testObservableToDisposeInParallel() {
        //To obtain a disposable, just use the object returned by subscribe
        Observable<Long> observable = Answer5_Specific_Observable.getObservableToDispose();
        Disposable disposable = observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);
                        },
                        Throwable::printStackTrace,
                        () -> PrintInColorKt.println("OnComplete is never called " + Thread.currentThread().getName(),
                                PrintInColorKt.ANSI_PURPLE));
        PrintInColorKt.println("Interval is not blocking " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        Disposable disposable2 = observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);
                        },
                        Throwable::printStackTrace,
                        () -> PrintInColorKt.println("OnComplete is never called " + Thread.currentThread().getName(),
                                PrintInColorKt.ANSI_PURPLE));
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PrintInColorKt.println("First disposable is disposed " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_BLUE);
        disposable.dispose();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PrintInColorKt.println("Second disposable is disposed " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_BLACK);
        disposable2.dispose();
    }

    @Test
    public void testSingle() {
        SingleObserver<Long> singleObserver = new SingleObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                //no used here
            }

            @Override
            public void onSuccess(Long value) {
                PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
        //To obtain a disposable, just use the object returned by subscribe
        Answer5_Specific_Observable.getSingle()
                .subscribe(singleObserver);

    }

    @Test
    public void testMayBe() {
        MaybeObserver<Long> maybeObserver = new MaybeObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                //no used here
            }

            @Override
            public void onSuccess(Long value) {
                PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                PrintInColorKt.println("MayBe is over [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }
        };
        PrintInColorKt.println("Testing empty " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_PURPLE);
        //To obtain a disposable, just use the object returned by subscribe
        Answer5_Specific_Observable.getMayBeEmpty()
                .subscribe(maybeObserver);
        PrintInColorKt.println("Testing one item " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_PURPLE);
        //To obtain a disposable, just use the object returned by subscribe
        Answer5_Specific_Observable.getMayBeWithOneItem()
                .subscribe(maybeObserver);


    }

    /**
     * OutPut result:
     * Testing Completable main
     * a Long operation Happened here in main
     * Completable is over [Thread] main
     */
    @Test
    public void testCompletable() {
        CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                //no used here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                PrintInColorKt.println("Completable is over [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }
        };
        PrintInColorKt.println("Testing Completable " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_PURPLE);
        //To obtain a disposable, just use the object returned by subscribe
        Answer5_Specific_Observable.getCompletable()
                .subscribe(completableObserver);
    }
}