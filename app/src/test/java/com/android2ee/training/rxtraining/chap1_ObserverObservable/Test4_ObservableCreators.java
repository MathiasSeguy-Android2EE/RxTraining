package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import com.android2ee.training.rxtraining.PrintInColorKt;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test4_ObservableCreators {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    int sumTestRange = 0;

    @Test
    public void testObservableRange() {
        Answer4_Observable_Creators.getRange0_10().subscribe(value -> {
            sumTestRange = sumTestRange + value;
        });
        Assert.assertEquals(45, sumTestRange);
    }

    Long intervalValue = 0L;

    @Test
    public void testObservableInterval() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Disposable disposable = Answer4_Observable_Creators.getInterval().subscribe(
                longIntervalValue -> {
                    intervalValue = longIntervalValue;
                    System.out.println(longIntervalValue);
                },
                Throwable::printStackTrace,
                countDownLatch::countDown);
        try {
            countDownLatch.await(10500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(9L, 0L + intervalValue);
        disposable.dispose();
    }

    @Test
    public void testObservableEmpty() {
        AtomicBoolean jobIsDone = new AtomicBoolean(Boolean.TRUE);
        Answer4_Observable_Creators.getEmpty().subscribe(
                letter -> Assert.fail("Empty should not emit data"),
                Throwable::printStackTrace,
                () -> jobIsDone.set(Boolean.TRUE));
        Assert.assertTrue(jobIsDone.get());
    }

    @Test
    public void testObservableNever() {
        Answer4_Observable_Creators.getNever().subscribe(
                letter -> Assert.fail("Never should not emit data"),
                Throwable::printStackTrace,
                () -> Assert.fail("Never should not emit data"));
    }

    @Test
    public void testObservableError() {
        Answer4_Observable_Creators.getError().subscribe(
                letter -> Assert.fail("Error should not emit data"),
                thr -> Assert.assertEquals(IllegalAccessError.class, thr.getClass()),
                () -> Assert.fail("Error should not emit data"));
    }

    /***********************************************************
     *  Understanding Defer:
     *  It will rerun the factory your provide to it
     **********************************************************/

    int numberOfLoop = 0, numberOfLoop1 = 0, numberOfLoop2 = 0;

    /**
     * The output is showing that using defer will resolve the problem
     * Reference Obs toto integer 1
     * NoDefer Obs toto integer 1[Thread] RxComputationThreadPool-2
     * Reference Obs toto integer 2
     * NoDefer Obs toto integer 2[Thread] RxComputationThreadPool-2
     * WithDefer Obs toto integer 1[Thread] RxComputationThreadPool-3
     * Reference Obs toto integer 3
     * NoDefer Obs toto integer 3[Thread] RxComputationThreadPool-2
     * Reference Obs toto integer 4
     * NoDefer Obs toto integer 4[Thread] RxComputationThreadPool-2
     * WithDefer Obs toto integer 2[Thread] RxComputationThreadPool-3
     */
    @Test
    public void testObservableDefer2() {
        //using a simple observable, you can not change its behavior
        Observable observable = Answer4_Observable_Creators.getDefer();
        //using defer you can change the behavior by re-running the factory when each observer subscribe to it
        Observable observableDefered = Observable.defer(() -> Answer4_Observable_Creators.getDefer()/* The factory of the Defer*/);
        observableDefered.subscribe(letter -> {
                    numberOfLoop++;
                    System.out.println("Reference " + letter + " " + numberOfLoop);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called

        //Without Defer, the same observer is used, so your changes won't be took into account
        Answer4_Observable_Creators.timeInterval = 2;
        observable.subscribe(letter -> {
                    numberOfLoop1++;
                    PrintInColorKt.println("NoDefer " + letter + " " + numberOfLoop1 + "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_BLUE);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called
//        //Proof of no changes
        Assert.assertEquals(numberOfLoop, numberOfLoop1);

        //With Defer, the observer is recreated by calling again Answer4_Observable_Creators.getDefer(),
        // so your changes will be took into account because a brand new Observable is created
        observableDefered.subscribe(letter -> {
                    numberOfLoop2++;
                    PrintInColorKt.println("WithDefer " + letter + " " + numberOfLoop2 + "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called
        //Proof of changes
        Assert.assertEquals(numberOfLoop * 2, numberOfLoop2);

        //wait 6 iteration
        try {
            Thread.sleep(6_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}