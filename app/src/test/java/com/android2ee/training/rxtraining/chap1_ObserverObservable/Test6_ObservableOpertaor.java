package com.android2ee.training.rxtraining.chap1_ObserverObservable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.disposables.Disposable;

import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.assertListEquals;
import static com.android2ee.training.rxtraining.chap1_ObserverObservable.TestUtils.assertLongListEquals;
// 

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to :
 */
public class Test6_ObservableOpertaor {
    private static final String TAG = "ObserverObservableTest";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * OutPut result:
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testFiltered() {
        Answer6_ObservableOpertaor.getObservableFiltered()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            Assert.assertTrue(value.toLowerCase().charAt(0) == 's');
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));
    }

    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    @Test
    public void testTakeElement() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Monday", "Tuesday", "Wednesday");
        Answer6_ObservableOpertaor.getObservableTakeElement()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }


    /**
     * OutPut result:
     * value is 0
     * value is 1
     * value is 2
     * OnComplete is called
     */
    @Test
    public void testObservableTakeDuration() {
        List<Long> receivedItems = new ArrayList(7);
        List<Long> expectedItems = Arrays.asList(0L, 1L, 2L);
        AtomicBoolean isJobFinished = new AtomicBoolean(Boolean.FALSE);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Answer6_ObservableOpertaor.getObservableTakeDuration()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("OnComplete is called");
                            countDownLatch.countDown();
                        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertLongListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * OnComplete is called
     */
    @Test
    public void testObservableTakeWhile() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Monday", "Tuesday");
        Answer6_ObservableOpertaor.getObservableTakeWhile()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    @Test
    public void testObservableTakeUntil() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Monday", "Tuesday", "Wednesday");
        Answer6_ObservableOpertaor.getObservableTakeUntil()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testObservableSkip() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Thursday", "Friday", "Saturday", "Sunday");
        Answer6_ObservableOpertaor.getObservableSkipElement()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testObservableTakeLast() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Friday", "Saturday", "Sunday");
        Answer6_ObservableOpertaor.getObservableTakeLastElement()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }


    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * OnComplete is called
     */
    @Test
    public void testObservableSkipLast() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday");
        Answer6_ObservableOpertaor.getObservableSkipLastElement()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }


    /**
     * OutPut result:
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testObservableSkipWhile() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        Answer6_ObservableOpertaor.getObservableSkipWhileElement()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * value is 2
     * value is 3
     * value is 4
     * OnComplete never called
     */
    @Test
    public void testObservableSkipDuration() {
        List<Long> receivedItems = new ArrayList(7);
        List<Long> expectedItems = Arrays.asList(2L, 3L, 4L);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Disposable disposable = Answer6_ObservableOpertaor.getObservableSkipDuration()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("OnComplete is called");
                            countDownLatch.countDown();
                        });
        try {
            countDownLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
        assertLongListEquals(receivedItems, expectedItems);
    }

    /**
     * OutPut result:
     * vvalue is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testObservableDistinct() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        Answer6_ObservableOpertaor.getObservableDistinct()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }


    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Tuesday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    @Test
    public void testObservableDistinctUntilChanges() {
        List<String> receivedItems = new ArrayList(7);
        List<String> expectedItems = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Tuesday", "Saturday", "Sunday");
        Answer6_ObservableOpertaor.getObservableDistinctUntilChanges()
                .subscribe(value -> {
                            System.out.println("value is " + value);
                            receivedItems.add(value);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("OnComplete is called"));

        assertListEquals(receivedItems, expectedItems);
    }
}