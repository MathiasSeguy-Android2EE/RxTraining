package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test1_Observable {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testObservableCreate() {
        Answer1_Observable.getDaysOfWeekSource().subscribe(letter -> {
            System.out.println(letter);
        });
    }

    @Test
    public void testObservableJust() {
        Answer1_Observable.getDaysOfWeekSourceWithJust().subscribe(letter -> {
            System.out.println(letter);
        });
    }


    @Test
    public void testObservableFromIterable() {
        Answer1_Observable.getDaysOfWeekSourceWithJust().subscribe(letter -> {
            System.out.println(letter);
        });
    }
}