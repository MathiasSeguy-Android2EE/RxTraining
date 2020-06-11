# Chapter 2: Observer

Creating an Observer to listen for the data stream of observables. Let's
suppose we have the observable from chapter one:

```java
Observable<String> observable= Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
```

We would like to know the number of days in a week emitted, so we need
to be sure the emission is finished. And we will test it like this:

```java
        observable.subscribe(observer);
        Assert.assertEquals(Boolean.TRUE, Answer2_Observer.jobIsDone);
        Assert.assertEquals(7,0+ Answer2_Observer.numberOfDayInTheWeek);
```

The goal is to create the ``` observer ```

## subscribe() with an observer instance

So let's create an instance of an observer

```java
int dayOfWeekNumber = 0;
new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //Will be understand later
            }
            @Override
            public void onNext(String dayOfWeek) {
                dayOfWeekNumber++;
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
```

## subscribe() with lambdas

Your just need to pass directly the lambda function to the method
**subscribe** as its parameters. It's the usual way.

```java
    private static int dayOfWeekNumber = 0;

    public static void createObserverLambda(Observable<String> observable) {
        //Do the same but using lambda
        Disposable disposable=observable.subscribe(
                Answer2_Observer::onNextDay,// OnNext using method shortcut call
                Throwable::printStackTrace,//onError same method shortcut
                () -> {jobIsDone=true;}//onComplete using explicit lambda
        );
    }
    /** Business method*/
    private static void onNextDay(String str){
        dayOfWeekNumber++;
        numberOfDayInTheWeek++;
        System.out.println("Day " + dayOfWeekNumber + " " + str);
    }
```

## The three lambda ways

You can omit onError (but you shouldn't) and onComplete (if you want).  
Let's have a remark before keep going. Lambda can be passed in different
ways:

```java
        // OnNext using method shortcut call
        observable.subscribe(
                Answer2_Observer::onNextDay
        );
        // OnNext using method shortcut call
        observable.subscribe(
                dayOfWeek -> onNextDay(dayOfWeek)
        );
        // OnNext using method shortcut call
        observable.subscribe(
                dayOfWeek -> {
                    onNextDay(dayOfWeek);
                }
        );
```

[Chapter 2: Observer]: #chapter-2-observer
[subscribe() with an observer instance]: #subscribe-with-an-observer-instance
[TableOfContent](index.md)


[Chapter 1 : Observables](Doc1_Observable.md)  
[Chapter 3: What happened when several observers subscribe to the same Observable? Hot Observable Versus Cold Observable](Doc3_SeveralSubscribing_ColdVsHot.md)
