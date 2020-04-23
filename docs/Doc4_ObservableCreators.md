# Chapter a: Observable simple creators
We will see a list of methods to create Observables. We already have seen:
- create()
- just()
- fromIterable()

Now we will others miscalaneous creators:
- range
- interval
- empty
- never
- error
- defer

```java
public static Observable<String> getColdObservables() {
        return Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
    }
```

A second one, more hot style, is a lottery like. Every second a number
is picked in 0..1_000, if this number is more than 800, we win and the
event is emitted. The loto is over when 5 winners are found.

```java
private static Boolean lotoIsOpen= Boolean.TRUE;
/**
 * Create an observable that emits a win event
 * Make a 1 second pause
 * @return
 */
public static Observable<Integer> getHotObservable() {
    //return a hot observable that emits the current second every second ten times
    Observable hotObserable = Observable.create(emiter -> {
        lotoIsOpen= Boolean.TRUE;
        long timeInMillis = System.currentTimeMillis();
        int numberOfWinner=0;
        while (lotoIsOpen) {
            int value=(int)(Math.random()*1_000);
            if(value>800){
                System.out.println("hotObserable[Thread]="+Thread.currentThread().getName()+"value is "+value);
                emiter.onNext(value);
                numberOfWinner++;
            }
            if(numberOfWinner>5){
                lotoIsOpen=Boolean.FALSE;
            }
            Thread.sleep(1_000);
        }
        emiter.onComplete();
    });
    return hotObserable;
}
```

Now let's create two observers that will observe those observables.

## 2 Observers subscribe() to a cold Observable in the same Thread

This will show that:
- the same set of data is always emitted to each observers.
- the observers will runs sequentially (first one has to finish before
  the second one starts) So the code is the following to test this
  behavior:

```Java
@Test
public void testColdObservable(){
    Observable<String> observable= Answer3_SeveralSubscribing_ColdVsHot.getColdObservables();
    //first subscriber
    AtomicBoolean jobIsDone= new AtomicBoolean(Boolean.FALSE);
    ArrayList<String> firstDataSet=new ArrayList<>(8);
    observable.subscribe(
            dayOfWeek->{
                firstDataSet.add(dayOfWeek);
            PrintInColorKt.println("First Observer value is "+dayOfWeek+"[Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_BLUE);
            },//onNext
            Throwable::printStackTrace,//onError
            () -> jobIsDone.set(Boolean.TRUE)//onComplete
    );
    //second subscriber
    Assert.assertEquals(Boolean.TRUE,jobIsDone.get());
    //insure both have the same set of data
    ArrayList<String> secondDataSet=new ArrayList<>(8);
    observable.subscribe(
            dayOfWeek->{
                secondDataSet.add(dayOfWeek);
                PrintInColorKt.println("Second Observer value is "+dayOfWeek+"[Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            },//onNext
            Throwable::printStackTrace,//onError
            () -> jobIsDone.set(Boolean.TRUE)//onComplete
    );
    for (int i = 0; i < firstDataSet.size(); i++) {
        Assert.assertEquals(firstDataSet.get(i),secondDataSet.get(i));
    }
}
```

The result is clear:

```text
First Observer value is Monday[Thread] main
First Observer value is Tuesday[Thread] main
First Observer value is Wednesday[Thread] main
First Observer value is Thursday[Thread] main
First Observer value is Friday[Thread] main
First Observer value is Saturday[Thread] main
First Observer value is Sunday[Thread] main
====== First Observer is finished ======
Second Observer value is Monday[Thread] main
Second Observer value is Tuesday[Thread] main
Second Observer value is Wednesday[Thread] main
Second Observer value is Thursday[Thread] main
Second Observer value is Friday[Thread] main
Second Observer value is Saturday[Thread] main
Second Observer value is Sunday[Thread] main
```

### Conclusion

The conclusion are the following:  
The subscribe() is blocking in onNext and loop in it until onComplete is
called or onError.  
The subscribe() is creating a new instance of the observables and rerun
it.

## 2 Observers subscribe() to a hot Observable in the same Thread

For starting to understand what happens, let's just make it simple:
subscribe twice to the hot observable and see what happens.

```java
@Test
public void testHotObservable(){
    Observable<Integer> observable= Answer3_SeveralSubscribing_ColdVsHot.getHotObservable();
    observable.subscribe(
            integer-> {
                System.out.println("First Observer value is "+integer+"[Thread] " + Thread.currentThread().getName());
            }
    );
    //The problem here is as both Observable and Observer are running in the same Thread
    //You have to wait for the first one to be finished to keep going
    observable.subscribe(
            integer-> {
                System.out.println("Second Observer value is "+integer+"[Thread] "+ Thread.currentThread().getName());
            }
    );
}
```

We obtain the following output:

```text
hotObserable[Thread]=mainvalue is 919
First Observer value is 919[Thread] main
hotObserable[Thread]=mainvalue is 852
First Observer value is 852[Thread] main
....
====== First Observer is finished ======
Second Observer value is 930[Thread] main
hotObserable[Thread]=mainvalue is 930
Second Observer value is 930[Thread] main
hotObserable[Thread]=mainvalue is 824   
....
```

### Conclusion

The first remark is that both observers run sequentially and they don't
receive the same set of data.  
The second remark is that hot or cold is a vision of the spirit, because
in both cases, the important fact to have in mind, is that subscribe is
cloning the Observable and run again its creation method.  
The conclusion is the same:  
The subscribe() is blocking in onNext and loop in it until onComplete is
called or onError.  
The subscribe() is creating a new instance of the observables and rerun
it. So if it emits events or random events, none observers will receives
the same data set.


## 3 Observers subscribe() to a hot Observable in the different Threads

For starting to understand what happens, let's just make it simple:
subscribe twice to the hot observable and run the Observable and the
Observable in another thread and see what happens.  
To switch Thread we will use subscribeOn, we will see in another
chapter.

```java
    @Test
    public void testHotObservableAsync(){
        Observable<Integer> observable= Answer3_SeveralSubscribing_ColdVsHot.getHotObservable();
        //To solve the problem, use subscribeOn to run the threatment in another thread
        observable.subscribeOn(Schedulers.computation())
        .subscribe(
                integer-> {
                    PrintInColorKt.println("First Observer value is "+integer+"[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_BLUE);
                }
        );
        AtomicBoolean jobIsDone= new AtomicBoolean(Boolean.FALSE);
        //To solve the problem, use subscribeOn to run the threatment in another thread
        observable.subscribeOn(Schedulers.computation())
        .subscribe(
                integer-> {
                    PrintInColorKt.println("Second Observer value is "+integer+"[Thread] "+ Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);

                },
                Throwable::printStackTrace,
                () -> jobIsDone.set(Boolean.TRUE)
        );

        //you need to wait for the jobs to finish
        while(!jobIsDone.get()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
```

The output is :

```
hotObserable[Thread]=RxComputationThreadPool-2value is 925
Second Observer value is 925[Thread] RxComputationThreadPool-2
hotObserable[Thread]=RxComputationThreadPool-1value is 957
First Observer value is 957[Thread] RxComputationThreadPool-1
hotObserable[Thread]=RxComputationThreadPool-2value is 875
Second Observer value is 875[Thread] RxComputationThreadPool-2
hotObserable[Thread]=RxComputationThreadPool-1value is 995
First Observer value is 995[Thread] RxComputationThreadPool-1
hotObserable[Thread]=RxComputationThreadPool-2value is 992
Second Observer value is 992[Thread] RxComputationThreadPool-2
hotObserable[Thread]=RxComputationThreadPool-2value is 911
Second Observer value is 911[Thread] RxComputationThreadPool-2
hotObserable[Thread]=RxComputationThreadPool-1value is 896
First Observer value is 896[Thread] RxComputationThreadPool-1
```

### Conclusion

Two remarks:
- Both observers receieved different events
- Both observers receive events in random order, it's not alternating
  first/second.

The conclusion is that both observers run in parallel and they **still
don't receive the same set of data.**  
So it's not a question of timing, subscribe duplicates the observables
and create a new instance each time.


# ConnectableObservable is the solution to receive from Hot observer the same events

ConnectableObserver is a way to change a "hot observables to a cold
one".  
For real, it will wait all the observers to subscribed to it
before starting the emission of events. That way all the observers will
receive the same set of data.

To change a normal observable to a ConnectableObservable, we just have to add
two lines of code:  
After the creation of the Observable, we need to call publish.

```java
getHotObservable().publish();
```

When the observers have been subscribing to the observable, you call
connect on it, it will launch the emission.

```java
//connectableObservable will act as a Hot observer as soon as you call connect
ConnectableObservable<String> connectableObservable= Answer3_SeveralSubscribing_ColdVsHot.getConnectableObservable();
//subscribe your first Observer
connectableObservable.subscribe(
        dayOfWeek->{
            System.out.println("First subscriber "+dayOfWeek+" in["+Thread.currentThread().getName()+"]");
        });
//first subscriber has finished
//second subscriber
connectableObservable.subscribe(
        dayOfWeek->{
            System.out.println("Second subscriber "+dayOfWeek+" in["+Thread.currentThread().getName()+"]");
        },//onNext
);
//then connect
System.out.println("Calling connect");
connectableObservable.connect();
System.out.println("Connect is over");
```

Using this pattern, we will receive in both observers the same dataset, sequentially:

```text
 Calling connect
 hotObserable[Thread]=mainvalue is 849
 First subscriber 849 in[main]
 Second subscriber 849 in[main]
 hotObserable[Thread]=mainvalue is 855
 First subscriber 855 in[main]
 Second subscriber 855 in[main]
 Subscribing with the late subscriber
 ...
 Connect is over
```

### Conclusion
Only one instance of the Observable is created.  
The onNext method runs the one after the other, in a sequential non blocking way.  
Calling connect() is blocking the Thread until the emission is over.




[Chapter 3: What happened when several observers subscribe to the same Observable? Hot Observable Versus Cold Observable](Doc3_SeveralSubscribing_ColdVsHot.md)

[What to remember]: #what-to-remember

