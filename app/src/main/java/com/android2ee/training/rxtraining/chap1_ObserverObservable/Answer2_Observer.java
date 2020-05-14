package com.android2ee.training.rxtraining.chap1_ObserverObservable;
//

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Mathias Seguy also known as Android2ee on 16/04/2020.
 * The goal of this class is to understand observable creation
 */
public class Answer2_Observer {
    private static final String TAG = "Observer_Answer2";
    static Long numberOfDayInTheWeek = 0L;
    static Boolean jobIsDone = Boolean.FALSE;

    public static Observer createObserverVerbose() {
        //TODO create and return an Observer that consumes days of the week received
        //Implements the methods onNext/onError/onComplete

        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //Will be understand later
            }

            int dayOfWeekNumber = 0;

            @Override
            public void onNext(String dayOfWeek) {
                dayOfWeekNumber++;
                numberOfDayInTheWeek++;
                System.out.println("Day " + dayOfWeekNumber + " " + dayOfWeek);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Problem happened");
            }

            @Override
            public void onComplete() {
                System.out.println("Week is over");
                jobIsDone = Boolean.TRUE;
            }
        };
    }

    public static void createObserverLambda(Observable<String> observable) {
        //Do the same but using lambda
        Disposable disposable = observable.subscribe(
                Answer2_Observer::onNextDay,// OnNext using method shortcut call
                Throwable::printStackTrace,//onError same method shortcut
                () -> {
                    jobIsDone = true;
                }//using explicit lambda
        );
//        // OnNext using method shortcut call
//        observable.subscribe(
//                Answer2_Observer::onNextDay
//        );
//        // OnNext using method shortcut call
//        observable.subscribe(
//                dayOfWeek -> onNextDay(dayOfWeek)
//        );
//        // OnNext using method shortcut call
//        observable.subscribe(
//                dayOfWeek -> {
//                    onNextDay(dayOfWeek);
//                }
//        );
    }

    private static int dayOfWeekNumber = 0;

    private static void onNextDay(String str) {
        dayOfWeekNumber++;
//        numberOfDayInTheWeek++;
        System.out.println("Day " + dayOfWeekNumber + " " + str);
    }
}
