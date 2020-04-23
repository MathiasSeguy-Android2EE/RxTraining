package com.android2ee.training.rxtraining.chap1_ObserverObservable.questions;
// 

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

/**
 * Created by Mathias Seguy also known as Android2ee on 17/04/2020.
 * The goal of this class is to explain the difference between Cold and Hot:
 * Cold Observables:
 * They will emits the same dataset to each observer when ever this observer register itself
 * It's like a playing a CD
 * Think about it like a data stream. Each observers will have its own stream of data.
 * Ex: Just,  fromIterable (because data set is finite)
 * All the Observables from the prevoious exercices 1, 2 are cold
 * <p>
 * Hot Observer:
 * They will emits a stream who ever is listening.
 * If observers register late, they don't get the previous emitted messages
 * It's like the Radio, you listen what is brodcasted, not what was broadcasted
 * Think about it like a stream of events. Each observers share the stream of events.
 * Ex: create can have an infinite loop that emits in the time and cannot replay the previous emission it has done
 * <p>
 * ConnectableObservables:
 * Help switch from cold to hot
 */
public class Question3_SeveralSubscribing_ColdVsHot {
    public static Observable<String> getColdObservables() {
        //Return a cold observables of the day of the week
        return null;
    }

    public static Observable<Integer> getHotObservable() {
        //return a hot observable that emits 1,2,3,4,5,6,... every second ten times
        return null;
    }

    public static ConnectableObservable<String> getConnectableObservable() {
        //return a hot observable that emits the cold observable getColdObservables
        //once and for all subscribers (before the connect call)
        return null;
    }
}
