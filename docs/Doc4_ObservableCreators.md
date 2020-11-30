This chapter is a small part of a biggest training project, you can find on [Github](https://github.com/MathiasSeguy-Android2EE/RxTraining).
You'll have the main branch with the answers/responses/unit tests and documentation. You could start with the "questions" branch to only have the questions
You'll have the full table of content of the articles on [Medium](https://medium.com/@android2ee)
You can download the book on [Android2ee](https://www.android2ee.com/)

# Chapter 4: Observable simple creators

We will see a list of methods to create Observables. We already have
seen:
- create()
- just()
- fromIterable()

Now we will have a look at others useful creators:
- range
- interval

And some more miscellaneous ones:
- empty
- never
- error

We will finished by looking at the Defer creator and its factory.
- defer


## [Range]

Range let you emit a stream of integer

```java
    public static Observable<Integer> getRange0_10() {
        //using the method Range
        return Observable.range(0, 10);
    }
```

I will emit the first 11 integer. You can also emit long using rangeLong

## [Interval]

I will let you emit a tick every second or any period you want:

```java
    public static Observable<Long> getInterval() {
        //using the method Interval
        return Observable.interval(1, TimeUnit.SECONDS);
    }
```

You also have some range to add an initial delay, to use a specific
scheduler or use intervalRange for emitting integer or long.  
** WARNING when using interval, the subscribe is not blocking the Thread.  
 It's a non blocking observable. It will call the method and drop it in the Thread.**


## [Empty, never and error]

In some occasions, you would like to emit either:
- an empty stream with its onComplete event
- an empty stream without the onComplete event
- an error event


```java
    /**
     * @return An observable that emits nothing and fire onComplete
     */
    public static Observable<String> getEmpty() {
        //using the method Empty
        return Observable.empty();
    }

    /**
     * @return An observable that emits nothing and never fire onComplete
     */
    public static Observable<String> getNever() {
        //using the method Never
        return Observable.never();
    }

    /**
     * @return An observable that emits only one Error and close
     */
    public static Observable<String> getError() {
        //using the method Error
        return Observable.error(new IllegalAccessError("You are not allowed"));
    }
```

## [The Defer]

In the previous chapter, we saw that calling subscribe is "cloning" the
Observable and run it again.  
What can we do if we want that the cloning
is in fact a new instantiation ? for example, the Observable uses
internal variable states that has to be updated permanently for each
observer.

Let's take an example, you have an observable that tick every n seconds where n is a class variable:
```java
public static int timeInterval = 1;
/**
 * do not create the Observable until the observer subscribes,
 * and create a fresh Observable for each observer
 *
 * @return An observable that can update its internal state
 */
public static Observable<String> getDefer() {
    //Create a ticker that emits every second: Defer or not defer, nothing changes
    Observable<String> observable2 = Observable
            .interval(timeInterval, TimeUnit.SECONDS)
            .map(integer -> {
                return "Obs " + Answer4_Observable_Creators.text + " integer";
            });
    return observable2;
    }
```
In another class, we want to use this ticker, but a first observer want a tick every second, and the other one, want a tick every two seconds:
```java
//using a simple observable, you can not change its behavior
        Observable observable=Answer4_Observable_Creators.getDefer();
        //using defer you can change the behavior by re-running the factory when each observer subscribe to it
        observable.subscribe(letter -> {
                    numberOfLoop++;
                    System.out.println("Reference "+letter + " " + numberOfLoop);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called

        //Without Defer, the same observable instance is used, so your changes won't be took into account
        Answer4_Observable_Creators.timeInterval = 2;
        observable.subscribe(letter -> {
                    numberOfLoop1++;
                    PrintInColorKt.println("NoDefer "+letter + " " + numberOfLoop1+ "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_BLUE);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called
        //Proof of no changes
        Assert.assertEquals(numberOfLoop,numberOfLoop1);
```
The result is clear, both observers receive the event every second:

```textmate
Reference Obs toto integer 1
NoDefer Obs toto integer 1[Thread] RxComputationThreadPool-2
Reference Obs toto integer 2
NoDefer Obs toto integer 2[Thread] RxComputationThreadPool-2
Reference Obs toto integer 3
NoDefer Obs toto integer 3[Thread] RxComputationThreadPool-2
Reference Obs toto integer 4
NoDefer Obs toto integer 4[Thread] RxComputationThreadPool-2
Reference Obs toto integer 5
NoDefer Obs toto integer 5[Thread] RxComputationThreadPool-2
Reference Obs toto integer 6
NoDefer Obs toto integer 6[Thread] RxComputationThreadPool-2
```
What we need is to be able to recreate the Observable when a new observer subscribe on it. To do that we will use a Observable factory that will recreate the Observable every time a new observer subscribe on it:
```java
Observable observableDefered=Observable.defer( () -> Answer4_Observable_Creators.getDefer()/* The factory of the Defer*/);
```
So now, every times a new observer is subscribing to the ObservableDefered, it will instanciate a new Observable using the factory.  
And it will just work:
```java
 @Test
    public void testObservableDefer2() {
        //using defer you can change the behavior by re-running the factory when each observer subscribe to it
        Observable observableDefered=Observable.defer( () -> Answer4_Observable_Creators.getDefer()/* The factory of the Defer*/);
        observableDefered.subscribe(letter -> {
                    numberOfLoop++;
                    System.out.println("Reference "+letter + " " + numberOfLoop);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called

        Answer4_Observable_Creators.timeInterval = 2;
        //With Defer, the observable instance is recreated by calling again Answer4_Observable_Creators.getDefer(),
        // so your changes will be took into account because a brand new Observable is created
        observableDefered.subscribe(letter -> {
                    numberOfLoop2++;
                    PrintInColorKt.println("WithDefer "+letter + " " + numberOfLoop2+ "[Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);
                },
                th -> System.out.println("UndeliverableException should be received"),//not called
                () -> System.out.println("On complete is called"));//not called
        //Proof of changes
        Assert.assertEquals(numberOfLoop*2,numberOfLoop2);

        //wait 10 iteration
        try {
            Thread.sleep(6_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```
The result is also clear, the second Observer will receive the event every 2 seconds, while the first will keep receiving them every second.
```textmate
Reference Obs toto integer 1
Reference Obs toto integer 2
WithDefer Obs toto integer 1[Thread] RxComputationThreadPool-2
Reference Obs toto integer 3
Reference Obs toto integer 4
WithDefer Obs toto integer 2[Thread] RxComputationThreadPool-2
```
### Conclusion
In some case, you would like to update the Observable every time a new observer is subscribing on it. To do that a simple trick is to use Defer and its factory.  
You'll be able to recreate your observable.


[Chapter 3: What happened when several observers subscribe to the same Observable? Hot Observable Versus Cold Observable](Doc3_SeveralSubscribing_ColdVsHot.md)
[Chapter 5 : Dispoable, Single, Maybe and Completable concepts](Doc5_SpeicifcObservables.md)  
[TableOfContent](index.md)

[Range]: #range
[Interval]: #interval
[Empty, never and error]: #empty-never-and-error
[The Defer]: #the-defer



