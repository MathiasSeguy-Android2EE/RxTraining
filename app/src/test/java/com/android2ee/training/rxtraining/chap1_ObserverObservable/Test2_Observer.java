package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test2_Observer {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testObserver() {
        Observer observer = Answer2_Observer.createObserverVerbose();
        Observable<String> observable = Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        observable.subscribe(observer);
        Assert.assertEquals(Boolean.TRUE, Answer2_Observer.jobIsDone);
        Assert.assertEquals(7, 0 + Answer2_Observer.numberOfDayInTheWeek);
    }

    @Test
    public void testObserverLambda() {
        Observable<String> toto = Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        Answer2_Observer.createObserverLambda(toto);
        Assert.assertEquals(7, 0 + Answer2_Observer.numberOfDayInTheWeek);
        Assert.assertEquals(Boolean.TRUE, Answer2_Observer.jobIsDone);
    }

}