package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import com.android2ee.training.rxtraining.PrintInColorKt;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test3_SeveralSubscribing_ColdVsHot {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Plays sequentially
     * Observe the same set of data (normal it's a clone of the observable when calling subscribe)
     * First Observer value is Monday[Thread] main
     * First Observer value is Tuesday[Thread] main
     * First Observer value is Wednesday[Thread] main
     * First Observer value is Thursday[Thread] main
     * First Observer value is Friday[Thread] main
     * First Observer value is Saturday[Thread] main
     * First Observer value is Sunday[Thread] main
     * Second Observer value is Monday[Thread] main
     * Second Observer value is Tuesday[Thread] main
     * Second Observer value is Wednesday[Thread] main
     * Second Observer value is Thursday[Thread] main
     * Second Observer value is Friday[Thread] main
     * Second Observer value is Saturday[Thread] main
     * Second Observer value is Sunday[Thread] main
     */
    @Test
    public void testColdObservable() {
        Observable<String> observable = Answer3_SeveralSubscribing_ColdVsHot.getColdObservables();
        //first subscriber
        AtomicBoolean jobIsDone = new AtomicBoolean(Boolean.FALSE);
        ArrayList<String> firstDataSet = new ArrayList<>(8);
        observable.subscribe(
                dayOfWeek -> {
                    firstDataSet.add(dayOfWeek);
                    PrintInColorKt.println("First Observer value is " + dayOfWeek + "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_BLUE);
                },//onNext
                Throwable::printStackTrace,//onError
                () -> jobIsDone.set(Boolean.TRUE)//onComplete
        );
        //second subscriber
        Assert.assertEquals(Boolean.TRUE, jobIsDone.get());
        //insure both have the same set of data
        ArrayList<String> secondDataSet = new ArrayList<>(8);
        observable.subscribe(
                dayOfWeek -> {
                    secondDataSet.add(dayOfWeek);
                    PrintInColorKt.println("Second Observer value is " + dayOfWeek + "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);
                },//onNext
                Throwable::printStackTrace,//onError
                () -> jobIsDone.set(Boolean.TRUE)//onComplete
        );
        for (int i = 0; i < firstDataSet.size(); i++) {
            Assert.assertEquals(firstDataSet.get(i), secondDataSet.get(i));
        }
    }

    /**
     * This test shows that an obsrver that emits hotly 10 items
     * 2 remarks:
     * Thread => subscribe play sequentially
     * New instance of the observable when calling subscribe
     * The output is:
     * hotObserable[Thread]=mainvalue is 919
     * First Observer value is 919[Thread] main
     * hotObserable[Thread]=mainvalue is 852
     * First Observer value is 852[Thread] main
     * hotObserable[Thread]=mainvalue is 955
     * First Observer value is 955[Thread] main
     * hotObserable[Thread]=mainvalue is 997
     * First Observer value is 997[Thread] main
     * hotObserable[Thread]=mainvalue is 873
     * First Observer value is 873[Thread] main
     * hotObserable[Thread]=mainvalue is 954
     * First Observer value is 954[Thread] main
     * hotObserable[Thread]=mainvalue is 930
     * ====== First Observer is finished ======
     * Second Observer value is 930[Thread] main
     * hotObserable[Thread]=mainvalue is 930
     * Second Observer value is 930[Thread] main
     * hotObserable[Thread]=mainvalue is 824
     * Second Observer value is 824[Thread] main
     * hotObserable[Thread]=mainvalue is 900
     * Second Observer value is 900[Thread] main
     * hotObserable[Thread]=mainvalue is 981
     * Second Observer value is 981[Thread] main
     * hotObserable[Thread]=mainvalue is 881
     * Second Observer value is 881[Thread] main
     */
    @Test
    public void testHotObservable() {
        Observable<Integer> observable = Answer3_SeveralSubscribing_ColdVsHot.getHotObservable();
        observable.subscribe(
                integer -> {
                    System.out.println("First Observer value is " + integer + "[Thread] " + Thread.currentThread().getName());
                }
        );
        //The problem here is as both Observable and Observer are running in the same Thread
        //You have to wait for the first one to be finished to keep going
        observable.subscribe(
                integer -> {
                    System.out.println("Second Observer value is " + integer + "[Thread] " + Thread.currentThread().getName());
                }
        );
    }

    /**
     * Run in //
     * otObserable[Thread]=RxComputationThreadPool-2value is 925
     * Second Observer value is 925[Thread] RxComputationThreadPool-2
     * hotObserable[Thread]=RxComputationThreadPool-1value is 957
     * First Observer value is 957[Thread] RxComputationThreadPool-1
     * hotObserable[Thread]=RxComputationThreadPool-2value is 875
     * Second Observer value is 875[Thread] RxComputationThreadPool-2
     * hotObserable[Thread]=RxComputationThreadPool-1value is 995
     * First Observer value is 995[Thread] RxComputationThreadPool-1
     * hotObserable[Thread]=RxComputationThreadPool-2value is 992
     * Second Observer value is 992[Thread] RxComputationThreadPool-2
     * hotObserable[Thread]=RxComputationThreadPool-2value is 911
     * Second Observer value is 911[Thread] RxComputationThreadPool-2
     * hotObserable[Thread]=RxComputationThreadPool-1value is 896
     * First Observer value is 896[Thread] RxComputationThreadPool-1
     */
    @Test
    public void testHotObservableAsync() {
        Observable<Integer> observable = Answer3_SeveralSubscribing_ColdVsHot.getHotObservable();
        //To solve the problem, use subscribeOn to run the threatment in another thread
        observable.subscribeOn(Schedulers.computation()).subscribe(
                integer -> {
                    PrintInColorKt.println("First Observer value is " + integer + "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_BLUE);
                }
        );
        //wait two second to see the delta in the log
//        try {
//            Thread.sleep(2_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        AtomicBoolean jobIsDone = new AtomicBoolean(Boolean.FALSE);
        //To solve the problem, use subscribeOn to run the threatment in another thread
        observable.subscribeOn(Schedulers.computation()).subscribe(
                integer -> {
                    PrintInColorKt.println("Second Observer value is " + integer + "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);

                },
                Throwable::printStackTrace,
                () -> jobIsDone.set(Boolean.TRUE)
        );

        //you need to wait for the jobs to finish
        while (!jobIsDone.get()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Run sequentially in the same Thread
     * Retrieve the same events set
     * Calling connect
     * hotObserable[Thread]=mainvalue is 849
     * First subscriber 849 in[main]
     * Second subscriber 849 in[main]
     * hotObserable[Thread]=mainvalue is 855
     * First subscriber 855 in[main]
     * Second subscriber 855 in[main]
     * Subscribing with the late subscriber
     */
    @Test
    public void testConnectableObservable() {
        //connectableObservable will act as a Hot observer as soon as you call connect
        ConnectableObservable<String> connectableObservable = Answer3_SeveralSubscribing_ColdVsHot.getConnectableObservable();
        //subscribe your first Observer
        AtomicBoolean jobIsDone = new AtomicBoolean(Boolean.FALSE);
        ArrayList<String> firstDataSet = new ArrayList<>(8);
        connectableObservable.subscribe(
                dayOfWeek -> {
                    firstDataSet.add(dayOfWeek);
                    System.out.println("First subscriber " + dayOfWeek + " in[" + Thread.currentThread().getName() + "]");
                },//onNext
                Throwable::printStackTrace,//onError
                () -> jobIsDone.set(Boolean.TRUE)//onComplete
        );
        //first subscriber has not started neither finish
        Assert.assertEquals(Boolean.FALSE, jobIsDone.get());
        //second subscriber
        ArrayList<String> secondDataSet = new ArrayList<>(8);
        connectableObservable.subscribe(
                dayOfWeek -> {
                    secondDataSet.add(dayOfWeek);
                    System.out.println("Second subscriber " + dayOfWeek + " in[" + Thread.currentThread().getName() + "]");
                },//onNext
                Throwable::printStackTrace,//onError
                () -> jobIsDone.set(Boolean.TRUE)//onComplete
        );
        //then connect
        System.out.println("Calling connect");
        connectableObservable.connect();

        for (int i = 0; i < firstDataSet.size(); i++) {
            Assert.assertEquals(firstDataSet.get(i), secondDataSet.get(i));
        }
        System.out.println("Subscribing with the late subscriber");
        //then test the behavior of the third one:Nothing will happened, the source is closed in a way
        connectableObservable.subscribe(
                dayOfWeek -> {
//                    secondDataSet.add(dayOfWeek);
                    System.out.println("Third subscriber received " + dayOfWeek + "Thread is " + Thread.currentThread().getName());
                },//onNext
                Throwable::printStackTrace,//onError
                () -> System.out.println("Third subscriber is finished")
                //onComplete
        );
    }

}