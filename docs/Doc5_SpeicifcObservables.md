# Chapter 5: Disposables and Specific Observables: Single/MayBe/Completable

We will see that every Observable is a Disposable object when
subscribed. It will show us how to stop an Observables when no more
observers needs him.

Then we will look at specific Observables :
- Single
- Maybe
- Completable


## [Disposable]

Every time you subscribe to an Observable, a Disposable is returned. So in the context of the Observer you can stop the observable.  
For example, let's consider this Observable:
```java
 public static Observable<Long> getObservableToDispose() {
        //using the method Interval
        return Observable.interval(1, TimeUnit.SECONDS);
    } 
```

```java
 Observable<Long> observable=Answer5_Specific_Observable.getObservableToDispose();
 Disposable disposable=observable
         .subscribe(value -> {
             PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                     PrintInColorKt.ANSI_GREEN);},
                 Throwable::printStackTrace,
                 ()->PrintInColorKt.println("OnComplete is never called " + Thread.currentThread().getName(),
                         PrintInColorKt.ANSI_PURPLE));
 PrintInColorKt.println("Interval is not blocking " + Thread.currentThread().getName(),
         PrintInColorKt.ANSI_RED);
 try {
     Thread.sleep(5_000);
 } catch (InterruptedException e) {
     e.printStackTrace();
 }
 disposable.dispose();
```
In the above example, you can see that after 5 seconds, the Observable is stopped by the context of the observer:
```textmate
Interval is not blocking main
Value= 0 [Thread] RxComputationThreadPool-1
Value= 1 [Thread] RxComputationThreadPool-1
Value= 2 [Thread] RxComputationThreadPool-1
Value= 3 [Thread] RxComputationThreadPool-1
Value= 4 [Thread] RxComputationThreadPool-1
```
Two questions raised from this example.
### What happened is two observers are listening to the observers and only one dispose the Observable ?
Only the Observer linked to the dispoable is dispose when dispose is called on it

```java
    @Test
    public void testObservableToDispose() {
        //To obtain a disposable, just use the object returned by subscribe
        Observable<Long> observable=Answer5_Specific_Observable.getObservableToDispose();
        Disposable disposable=observable
                .subscribe(value -> {
                    PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);});
        PrintInColorKt.println("Interval is not blocking " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
        PrintInColorKt.println("Let's try to use the Observable again " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        disposable=observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);});
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
    }
```
The output is clear, the second Observers is still running for the next 5s until it is also disposed.
```textmate
Interval is not blocking main
Value= 0 [Thread] RxComputationThreadPool-1
Value= 0 [Thread] RxComputationThreadPool-2
Value= 1 [Thread] RxComputationThreadPool-1
Value= 1 [Thread] RxComputationThreadPool-2
Value= 2 [Thread] RxComputationThreadPool-1
Value= 2 [Thread] RxComputationThreadPool-2
Value= 3 [Thread] RxComputationThreadPool-1
Value= 3 [Thread] RxComputationThreadPool-2
Value= 4 [Thread] RxComputationThreadPool-1
First disposable is disposed main
Value= 4 [Thread] RxComputationThreadPool-2
Value= 5 [Thread] RxComputationThreadPool-2
Value= 6 [Thread] RxComputationThreadPool-2
Value= 7 [Thread] RxComputationThreadPool-2
Value= 8 [Thread] RxComputationThreadPool-2
Value= 9 [Thread] RxComputationThreadPool-2
Second disposable is disposed main
```
### What happened when the Observable is dispose, can we reuse it ?
The answer is yes, we can reuse it.

```java
    @Test
    public void testObservableToDispose() {
        //To obtain a disposable, just use the object returned by subscribe
        Observable<Long> observable=Answer5_Specific_Observable.getObservableToDispose();
        Disposable disposable=observable
                .subscribe(value -> {
                    PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                            PrintInColorKt.ANSI_GREEN);});
        PrintInColorKt.println("Interval is not blocking " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
        PrintInColorKt.println("Let's try to use the Observable again " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_RED);
        disposable=observable
                .subscribe(value -> {
                            PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                                    PrintInColorKt.ANSI_GREEN);});
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disposable.dispose();
    }
```
And the output is:
```textmate
Interval is not blocking main
Value= 0 [Thread] RxComputationThreadPool-1
Value= 1 [Thread] RxComputationThreadPool-1
Value= 2 [Thread] RxComputationThreadPool-1
Value= 3 [Thread] RxComputationThreadPool-1
Value= 4 [Thread] RxComputationThreadPool-1
Let's try to use the Observable again main
Value= 0 [Thread] RxComputationThreadPool-2
Value= 1 [Thread] RxComputationThreadPool-2
Value= 2 [Thread] RxComputationThreadPool-2
Value= 3 [Thread] RxComputationThreadPool-2
```

### Conclusion
Disposable is a great feature that help us dispose the resource of the Observable.  
The Disposable is linked to the "instance" of the observable. It is returned by calling subscribe on it.  
If you dispose one observable, only its instance will be dispose.  
If you dispose an observable and then relaunch it, it will restart.  
Remark: The observable has a isDisposed method to check if it has to stop.


## [Single]
Single is a specific Observable that will emits only one item and then close by calling onComplete

```java
 public static Single<Long> getSingle() {
        return Single.just(1L);
    }
```
It has a specific Observer;
- OnNext method is changed by onSuccess method (because only one emission):
- OnComplete method is gone:

```java
SingleObserver<Long> singleObserver=new SingleObserver<Long>() {
    @Override
    public void onSubscribe(Disposable d) {
        //no used here
    }
    @Override
    public void onSuccess(Long value) {
        PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_GREEN);
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
};
```
## [Maybe]
Maybe is a specific Observable that will emits only one item xor the onComplete event


```java
/**
 * Using MayBe you will emit or not only one event and then close emitting onComplete
 * @return An observable that emits or not an item
 */
public static Maybe<Long> getMayBeEmpty() {
    return Maybe.empty();
}
/**
 * Using MayBe you will emit or not only one event and then close emitting onComplete
 * @return An observable that emits or not an item
 */
public static Maybe<Long> getMayBeWithOneItem() {
    return Maybe.just(1L);
}
```
It has a specific Observer;
- OnNext method is changed by onSuccess method (because only one emission)

```java
MaybeObserver<Long> maybeObserver=new MaybeObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                //no used here
            }

            @Override
            public void onSuccess(Long value) {
                PrintInColorKt.println("Value= " + value + " [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                PrintInColorKt.println("MayBe is over [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }
        };
```
Remark: Only one event is send, either success, either complete, either error.  
Wecan see this behavior by using the maybeObserver below in the following code:

```java
 @Test
    public void testMayBe() {
        PrintInColorKt.println("Testing empty " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_PURPLE);
        Answer5_Specific_Observable.getMayBeEmpty() .subscribe(maybeObserver);
        PrintInColorKt.println("Testing one item " + Thread.currentThread().getName(),
                PrintInColorKt.ANSI_PURPLE);
        //To obtain a disposable, just use the object returned by subscribe
        Answer5_Specific_Observable.getMayBeWithOneItem().subscribe(maybeObserver);
    }
```
The output is obvious:

```textmate
Tesing empty main
MayBe is over [Thread] main
Testing one item main
Value= 1 [Thread] main
```

## [Compleatble]
Completable is a specific Observable that will emits only onComplete. The goal is to run an operation and prevent observer that it's done.  
 You can use FromRunnable to create it. There are a lot of creators for this Observable.

```java
/**
 * Using Completable to emit only the onComplete event
 * @return An observable that only emit onComplete
 */
public static Completable getCompletable() {
//  return Completable.complete();//work just fine
    return Completable.fromRunnable(()->longOperation());
}
private static void longOperation(){
    System.out.println(" a Long operation Happened here in "+ Thread.currentThread().getName());
}
```

The associated Observer is the following.

It has a specific Observer;
- OnNext method is gone

```java
CompletableObserver completableObserver=new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                //no used here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                PrintInColorKt.println("Completable is over [Thread] " + Thread.currentThread().getName(),
                        PrintInColorKt.ANSI_GREEN);
            }
        };
```


[Chapter 4 : Observables Basics Creators](Doc4_ObservableCreators.md)  
[Chapter 5 : Dispoable, Single, Maybe and Completable concepts](Doc5_SpeicifcObservables.md)  
[TableOfContent](index.md)

[Disposable]: #disposable
[Single]: #single
[Maybe]: #maybe
[Compleatble]: #compleatble




