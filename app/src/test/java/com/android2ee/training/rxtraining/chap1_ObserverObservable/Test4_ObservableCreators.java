package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
            countDownLatch.await(10, TimeUnit.SECONDS);
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

    CountDownLatch countDownLatch = new CountDownLatch(1);
    int numberOfLoop = 0;

    @Test
    public void testObservableDefer() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Disposable disposable = Answer4_Observable_Creators.getDefer()
                .subscribeOn(Schedulers.computation())
                .doOnNext(letter -> {
                    numberOfLoop++;
                    switch (numberOfLoop) {
                        case 0:
                            Answer4_Observable_Creators.text = "Bob";
                            break;
                        case 1:
                            Answer4_Observable_Creators.text = "Joh";
                            break;
                        case 2:
                            Answer4_Observable_Creators.text = "Jhonny";
                            break;
                        case 3:
                            Answer4_Observable_Creators.text = "patate";
                            break;
                        case 4:
                            Answer4_Observable_Creators.text = "nouille";
                            break;
                        case 5:
                            Answer4_Observable_Creators.text = "hole";
                            break;
                        case 6:
                            Answer4_Observable_Creators.text = "quatre";
                            break;
                        default:
                            Answer4_Observable_Creators.text = "toto";
                    }
                    if (numberOfLoop == 3) {
                        System.out.println(numberOfLoop);
                        countDownLatch.countDown();
                    }
                })
                .subscribe(letter -> {
                            System.out.println(letter + " " + numberOfLoop);
                        },
                        th -> System.out.println("UndeliverableException should be received"),//not called
                        () -> System.out.println("On cmoplete is called"));//not called

//       Disposable dispo2= Answer4_Observable_Creators.getDefer().subscribe(item->{
//            System.out.println(item + " " + numberOfLoop);
//        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CountDown is dead");
        disposable.dispose();//hard crash with Thread.
//        dispo2.dispose();
    }
}