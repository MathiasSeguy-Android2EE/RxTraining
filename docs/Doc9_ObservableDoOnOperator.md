This chapter is a small part of a biggest training project, you can find on [Github](https://github.com/MathiasSeguy-Android2EE/RxTraining).
You'll have the main branch with the answers/responses/unit tests and documentation. You could start with the "questions" branch to only have the questions
You'll have the full table of content of the articles on [Medium](https://medium.com/@android2ee)
You can download the book on [Android2ee](https://www.android2ee.com/)

# Chapter 9: Observable's action operators
We will see the following actions on Observables:
To manage emitted events (we'll see that's not absolutely true) :
- doOnNext
- doOnEach

To manage the life cycle of the observer :
- doOnLifecycle
- doOnRequest
- doOnSubscribe
- doOnUnsubscribe
- doOnError
- doOnComplete
- doOnCompleted
- doOnDispose
- doOnTerminate
- doAfterTerminate
- doFinally

And those related to errors recovery :
- onErrorReturn
- OnErrorReturnItem
- onErrorResumeNext
- retry

For this chapter, we keep using the days of the week observable.

```java
   private static final Observable<String> observableSrc = Observable.just(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    );
```

But we will also use the interval observer :

```java
   private static final Observable<String> observableSrc = Observable.just(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    );
```

## doOnEach versus doOnNext
*Stream is not changed*
They will both do an action when the next item is called.  
doOnNext receives only the emitted items  
doOnEach receives all the events : onNext,OnError and onComplete  
So if you are only interested by the emitted items, use doOnNext.  
If you are interested by the whole lifecycle (next/error/complete), use doOnEach

### doOnEach
*Stream is not changed*
The behavior of doOnEach is clearly understandable using this code:

```java
    /**
    * Using the observableSrc we use doOnEach to understand its behavior
    */
    public static Observable<String> getObservableDoOnEach() {
        return observableSrc.doOnEach(it ->
        {
            System.out.print("onEach is " + it+" :");
            if(it.isOnNext()) {
                System.out.println("isOnNext = " + it.isOnNext() +" and getValue = " + it.getValue());
            }else if(it.isOnError()) {
                System.out.println("isOnError = " + it.isOnNext() +" and getError = " + it.getError());
            }else {
                System.out.println("isOnComplete = " + it.isOnComplete()+" no value associated");
            }
        });
    }
    /**
     * OutPut result:
     * onEach is OnNextNotification[Monday] :isOnNext = true and getValue = Monday
     * onEach is OnNextNotification[Tuesday] :isOnNext = true and getValue = Tuesday
     * onEach is OnNextNotification[Wednesday] :isOnNext = true and getValue = Wednesday
     * onEach is OnNextNotification[Thursday] :isOnNext = true and getValue = Thursday
     * onEach is OnNextNotification[Friday] :isOnNext = true and getValue = Friday
     * onEach is OnNextNotification[Saturday] :isOnNext = true and getValue = Saturday
     * onEach is OnNextNotification[Sunday] :isOnNext = true and getValue = Sunday
     * onEach is OnCompleteNotification :isOnComplete = true no value associated
     */
    @Test
    public void testDoOnEach() {
        Answer9_ObservableActions.getObservableDoOnEach()
                .subscribe();
    }
```


### doOnNext
The behavior of doOnNext is clearly understandable using this code:  
*Stream is not changed*

```java
    /**
    * Using the observableSrc we use doOnNext to understand its behavior
    */
    public static Observable<String> getObservableDoOnEach() {
        return observableSrc
                .doOnNext(it -> System.out.println("Size is " + it.length()));
    }
    /**
     * OutPut result:
     * doOnNext Size of Monday is 6
     * doOnNext Size of Tuesday is 7
     * doOnNext Size of Wednesday is 9
     * doOnNext Size of Thursday is 8
     * doOnNext Size of Friday is 6
     * doOnNext Size of Saturday is 8
     * doOnNext Size of Sunday is 6
     */
    @Test
    public void testDoOnNext() {
        Answer9_ObservableActions.getObservableDoOnEach()
                .subscribe();
    }
```
### Tracking the lifeCycle of the observable
We can schedule an action according to any event of the life cycle of the observable using one of those action operators:
- doOnLifecycle
- doOnSubscribe
- doOnComplete
- doOnCompleted
- doOnDispose
- doOnTerminate
- doAfterTerminate
- doFinally

The only important point is the difference between an observable that reaches the onComplete and those which are disposed before onComplete happens.  
If the observable reaches the onComplete then you'll receive the :
- OnComplete
- OnTerminate
- OnAfterTerminate  
If the observable is disposed before the onComplete is reached, you'll receive the :
- onDispose  
But you can not receives both events set, either it's the onComplete reached case, either it's the onDispose case.

To see that we will test both cases and see what happens:
### Reaching the onComplete
Let's test what happen with an observable that reaches the onComplete :
```java
     /**
     * Using the observableSrc 
     * use doOnSubscribe, doOnLifecycle, doOnComplete, doOnDispose, doOnTerminate,
     * doAfterTerminate, doFinally, doAfterNext, doOnNext
     * to understand its behavior
     * This case is the onComplete reached case     *
     * @return
     */
    public static Observable<String> getObservableLifecycleTrackerDoOn() {
        /**
         * Only trigger the onNext
         * Only have the value of the next item
         */
        return observableSrc
                .doOnSubscribe(disp -> System.out.println("doOnSubscribe:called with "+disp))
                .doOnLifecycle(
                        disposable -> System.out.println("doOnLifecycle0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("doOnLifecycle1:doOnDispose"))//the action to do in onDispose
                .doOnComplete(() -> System.out.println("doOnComplete:called"))
                .doOnDispose(() -> System.out.println("doOnDispose:called"))
                .doOnTerminate(() -> System.out.println("doOnTerminate:called"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate:called"))
                .doFinally(() -> System.out.println("doFinally:called"))
                .doAfterNext(it -> System.out.println("doAfterNext:called with "+it))
                .doOnNext(it -> System.out.println("doOnNext:called with "+it))
                ;
    }
    
    /**
     * OutPut result:
     * doOnSubscribe:called with io.reactivex.internal.operators.observable.ObservableFromArray$FromArrayDisposable@3c09711b
     * doOnLifecycle0:doOnSubscribe
     * doOnNext:called with Monday
     * value emitted is Monday
     * doAfterNext:called with Monday
     * ... blabla ...
     * doAfterNext:called with Sunday
     * doOnComplete:called <-onComplete called, onDispose not called
     * doOnTerminate:called <-onTerminate called, onDispose not called
     * onCompleteCalled <-onComplete called, onDispose not called
     * doFinally:called <-always called
     * doAfterTerminate:called <-doAfterTerminate called, onDispose not called
     * Disposing the observable while it still runs
     */
    @Test
    public void testObservableLifecycleTrackerDoOn() throws InterruptedException {
        Disposable disposable=Answer9_ObservableActions.getObservableLifecycleTrackerDoOn()
                .subscribe(it -> System.out.println("value emitted is "+it),
                        Throwable::printStackTrace,
                        ()->System.out.println("onCompleteCalled"));
        Thread.sleep(2500);
        System.out.println( "Disposing the observable while it still runs");
        disposable.dispose();
    }
```

### Reaching the onDispose
Let's test what happen with an observable that reaches the onDispose :

```java
    /**
     * Using the observableIntervalSrc 
     * use doOnSubscribe, doOnLifecycle, doOnComplete, doOnDispose, doOnTerminate,
     * doAfterTerminate, doFinally, doAfterNext, doOnNext
     * to understand its behavior
     * This case is the onDispose reached case     *
     * @return
     */
    public static Observable<Long> getObservableLifecycleTrackerDoOnWithInterval() {
        /**
         * Only trigger the onNext
         * Only have the value of the next item
         */
        return observableIntervalSrc
                .doOnSubscribe(disp -> System.out.println("doOnSubscribe:called with "+disp))
                .doOnLifecycle(
                        disposable -> System.out.println("doOnLifecycle0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("doOnLifecycle1:doOnDispose"))//the action to do in onDispose
                .doOnComplete(() -> System.out.println("doOnComplete:called"))
                .doOnDispose(() -> System.out.println("doOnDispose:called"))
                .doOnTerminate(() -> System.out.println("doOnTerminate:called"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate:called"))
                .doFinally(() -> System.out.println("doFinally:called"))
                .doAfterNext(it -> System.out.println("doAfterNext:called with "+it))
                .doOnNext(it -> System.out.println("doOnNext:called with "+it))
                ;
    }
    
    /**
     * OutPut result:
     * doOnSubscribe:called with null
     * doOnLifecycle0:doOnSubscribe
     * doOnNext:called with 0
     * value emitted is 0
     * doAfterNext:called with 0
     * doOnNext:called with 1
     * value emitted is 1
     * doAfterNext:called with 1
     * Disposing the observable while it still runs
     * doOnDispose:called <-onDispose called, onComplete, onTerminate, doAfterTerminate not called
     * doOnLifecycle1:doOnDispose
     * doFinally:called
     */
    @Test
    public void testObservableLifecycleTrackerDoOnWithInterval() throws InterruptedException {
        Disposable disposable=Answer9_ObservableActions.getObservableLifecycleTrackerDoOnWithInterval()
                .subscribe(it -> System.out.println("value emitted is "+it),
                        Throwable::printStackTrace,
                        ()->System.out.println("onCompleteCalled"));
        Thread.sleep(2500);
        System.out.println( "Disposing the observable while it still runs");
        disposable.dispose();
    }
```

## Error actions:
There are several types of actions you can do on error, to track them or recover, let's see that.  
So the first remark is that exception will break your observer chain and kill it by firing the exception.  
OnDispose is not called, onComplete neither, you just fall in the onError block of the Observer (the closest to the error) and receieves the onTerminate.  
But sometimes we want to intercept the error in the chain and changes the behavior from here.  
To dick into this error "management", we will use this observable:
```java
    private static final Observable<String> observableWithErrorSrc =Observable.create(
            emiter -> {
                emiter.onNext("Monday");
                emiter.onNext(null);
            }
    );
```
### Normal behavior when error happened
We will use the lifecycle operators to understand the behavior when an error is send.  
When the exception is thrown, it will break the RxChain and the following calls are done:
- onTerminate
- OnError
- The exception is thrown
- onFinnaly
- And onAfterTerminate
And your chain is over.
```java
    /**
     * Using the observableWithErrorSrc
     * use doOnSubscribe, doOnLifecycle, doOnComplete, doOnDispose, doOnTerminate,
     * doAfterTerminate, doFinally, doAfterNext, doOnNext
     * to understand its behavior
     * This case is the onDispose reached case     *
     * @return
     */
    public static Observable<String> getObservableLifecycleTrackerWithError() {
        return observableWithErrorSrc
                .doOnSubscribe(disp -> System.out.println("doOnSubscribe:called with "+disp))
                .doOnLifecycle(
                        disposable -> System.out.println("doOnLifecycle0:doOnSubscribe"),//The action to do in onSubscribe
                        () -> System.out.println("doOnLifecycle1:doOnDispose"))//the action to do in onDispose
                .doOnComplete(() -> System.out.println("doOnComplete:called"))
                .doOnDispose(() -> System.out.println("doOnDispose:called"))
                .doOnTerminate(() -> System.out.println("doOnTerminate:called"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate:called"))
                .doFinally(() -> System.out.println("doFinally:called"))
                .doAfterNext(it -> System.out.println("doAfterNext:called with "+it))
                .doOnNext(it -> System.out.println("doOnNext:called with "+it))
                .doOnError(th->System.out.println("doOnError:called with "+th.getClass().getSimpleName()))
                ;
    }
    /**
     * OutPut result:
     * doOnSubscribe:called with null
     * doOnLifecycle0:doOnSubscribe
     * doOnNext:called with Monday
     * value emitted is Monday
     * doAfterNext:called with Monday
     * doOnTerminate:called
     * doOnError:called with NullPointerException
     * exception thrown java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     * java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     * doFinally:called
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * doAfterTerminate:called
     * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     */
    @Test
    public void testObservableLifecycleTrackerWithError() {
        Answer9_ObservableActions.getObservableLifecycleTrackerWithError()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        th -> {
                            System.out.println("exception thrown " + th);
                            th.printStackTrace();
                        },
                        () -> System.out.println("onCompleteCalled"));
    }
```
### Dying cleanly
You cannot recover from an exception thrown. The only thing you can do is to have a last action before dying.  
So you can:
- retry the observation by restarting your chain
- emit an item and complete
- emit several items and complete
- run another observable and complete

The important fact to have in mind is that your chain is over. What ever you will do, in the best case, onComplete will be called and you can sent last items before dying. This is the only action you can do. You can not resume or keep going with the chain
In a way there is no possible recovery, you can only die nicely.  
### Dying nicely with onError**
You can use one of those 3 operators:
- onErrorReturn
- OnErrorReturnItem
- onErrorResumeNext

They will all act the same: Do the last action and complete the chain. You will avoid to fall in the onError bloc of the observer.  :

#### Using onErrorResumeNext
```java
    
    /**
     * Using the observableWithErrorSrc skip error and return twice "Buggy Day"
     * @return
     */
    public static Observable<String> getObservableWithOnErrorResumeNext() {
        return observableWithErrorSrc
                .onErrorResumeNext(Observable.just("Buggy day").repeat(2));
    }
    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Buggy day
     * value emitted is Buggy day
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorResumeNext() {
        Answer9_ObservableActions.getObservableWithOnErrorResumeNext()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }
```
#### Using onErrorReturnItem
```java
    
    /**
     * Using the observableWithErrorSrc skip error items and return a bad day item instead of the error
     * @return
     */
    public static Observable<String> getObservableWithOnErrorReturnItem() {
        //you can also return an empty Observable and then quit the Observable nicely
        return observableWithErrorSrc
                .onErrorReturnItem("Bad day");
    }
    /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Bad day
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorReturnItem() {
        Answer9_ObservableActions.getObservableWithOnErrorReturnItem()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }
```
#### Using onErrorReturn
```java
    
    /**
     * Using the observableWithErrorSrc skip error items and return a bad day item instead of the error
     * @return
     */
    public static Observable<String> getObservableWithOnErrorReturn() {
        return observableWithErrorSrc
                .onErrorReturn(th->"Bad day");
    }
   
    /**
     * OutPut result:d
     * value emitted is Monday
     * value emitted is Bad day
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorReturn() {
        Answer9_ObservableActions.getObservableWithOnErrorReturn()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }
```

#### Retry
You can also just rerun your Observable source and see if the exception happens again.  
You have to be aware that if you use this pattern and the exception raises again, you will just die, onComplete will not be called and you will fall in the onError bloc of the final observer.
```java
     /**
     * Using the observableWithErrorSrc skip error items and retry twice
     * @return
     */
    public static Observable<String> getObservableWithOnErrorRetry() {
        //you can also return an empty Observable and then quit the Observable nicely
        return observableWithErrorSrc
                .retry(2);
    }
   
  /**
     * OutPut result:
     * value emitted is Monday
     * value emitted is Monday
     * value emitted is Monday
     exception thrown java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     * java.lang.NullPointerException: onNext called with null. Null values are generally not allowed in 2.x operators and sources.
     */
    @Test
    public void testObservableWithOnErrorRecover() {
        Answer9_ObservableActions.getObservableWithOnErrorRetry()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        th -> {
                            System.out.println("exception thrown " + th);
                            th.printStackTrace();
                        },
                        () -> System.out.println("onCompleteCalled"));
    }
```
### A nice usual way to use onErrorResumeNext
A nice usual way to handle the exception is to emit and empty "item" and just onComplete nicely. This is more for your Rx-culture.  
(but we can wonder why you want to avoid to run in your onError bloc)
```java
    
    /**
     * Using the observableWithErrorSrc skip error items and return an empty observable
     * @return
     */
    public static Observable<String> getObservableWithOnErrorResumeNextEmpty() {
        //you can also return an empty Observable and then quit the Observable nicely
        return observableWithErrorSrc
                .onErrorResumeNext(Observable.empty());
    }
    /**
     * When returning an empty Observable, you can quit the process nicely
     * <p>
     * OutPut result:
     * value emitted is Monday
     * onCompleteCalled
     */
    @Test
    public void testObservableWithOnErrorResumeNextEmpty() {
        Answer9_ObservableActions.getObservableWithOnErrorResumeNextEmpty()
                .subscribe(it -> System.out.println("value emitted is " + it),
                        Throwable::printStackTrace,
                        () -> System.out.println("onCompleteCalled"));
    }
```


[chapter 8 : observables s transformer operators](Doc8_ObservableCollectionOperator.md)  
[TableOfContent](index.md)





[Chapter 9: Observable's action operators]: #chapter-9-observables-action-operators

