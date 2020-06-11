# [Chapter 6: Observable operators]
We will see the following operators on Observables:
- filter
- take
- takeWhile
- takeLast
- skip
- skipLast
- skipWhile
- distinct
- distinctUntilChanges



## filter

You can filter the output stream of the Observable by calling filter and provide for each item a filter rule
```java
  /**
  * OutPut result:
  * value is Saturday
  * value is Sunday
  * OnComplete is called
  */
 public static Observable<String> getObservableFiltered() {
        return observableSrc.filter(item->item.toLowerCase().charAt(0)=='s');
    }
```

## take
The take operators are the following:
- take: Take an n-elements or during n TimeUnits
- takeWhile: While the condition is true, take the elements
- takeUntil: When the condition is true, takes the elements after that
- takeLast: Take the last n elements
- elemenAt: Take the elements at the specific position and then call onComplete and dispose the observable
You can take** using a number or using a time value.
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
    private static final Observable<Long> observableIntervalSrc = Observable.interval(1, TimeUnit.SECONDS);

    /**
    * Take the 3 first elements of the list
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    public static Observable<String> getObservableTakeElement() {
        return observableSrc.take(3);
    }

    /**
    * Take the first elements during the 3 first seconds
     * OutPut result:
     * value is 0
     * value is 1
     * value is 2
     * OnComplete is called
     */
    public static Observable<Long> getObservableTakeDuration() {
        return observableIntervalSrc.take(3500,TimeUnit.MILLISECONDS);
    }

    /**
    * Take elements while the condition is true
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    public static Observable<String> getObservableTakeWhile() {
        return observableSrc.takeWhile(item->item.length()>7);
    }
    /**
    * Take elements until the condition is true
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    public static Observable<String> getObservableTakeWhile() {
        return observableSrc.takeWhile(item->item.length()>7);
    }

    /**
     * Using the observableSrc return the last 3 elements of the list
     */
    public static Observable<String> getObservableTakeLastElement() {
        return observableSrc.takeLast(3);
    }
```


## skip
The skip operators are the following:
- skip: Skip the n-elements or during n TimeUnits
- skipWhile: While the condition is true, skip the elements
- skipUntil: When the condition is true start skipping, takes the elements after that
- skipLast: Skip the last n elements (need to wait for the onComplete to be called)
You can skip** using a number or using a time value.
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
    private static final Observable<Long> observableIntervalSrc = Observable.interval(1, TimeUnit.SECONDS);

    /**
     * OutPut result:
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
     */
    public static Observable<String> getObservableSkipElement() {
        return observableSrc.skip(3);
    }
    
    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * OnComplete is called
     */
    public static Observable<String> getObservableSkipLastElement() {
        return observableSrc.skipLast(3);
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
    public static Observable<String> getObservableSkipWhileElement() {
        return observableSrc.skipWhile(item->item.length()>7);
    }

    /**
     * OutPut result:
     * value is 2
     * value is 3
     * value is 4
     * OnComplete never called because disposed after 5s
     */
    public static Observable<Long> getObservableSkipDuration() {
        return observableIntervalSrc.skip(3,TimeUnit.SECONDS);
    }
```

## distinct
The distinct operators are the following:
- distinct: Insure all the elements are different
- distinctUntilChanged: Insure two continuous elements are not the same
```java
  private static final Observable<String> observableSrcDuplicated = Observable.fromArray(
            "Monday",
            "Monday",
            "Tuesday",
            "Tuesday",
            "Wednesday",
            "Wednesday",
            "Thursday",
            "Thursday",
            "Friday",
            "Friday",
            "Tuesday",
            "Saturday",
            "Saturday",
            "Sunday",
            "Sunday"
    );

   /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    public static Observable<String> getObservableDistinct() {
        return observableSrcDuplicated.distinct();
    }

    /**
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Tuesday <- difference between disctinct and distinctUntilChange
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    public static Observable<String> getObservableDistinctUntilChanges() {
        return observableSrcDuplicated.distinctUntilChanged();
    }
```


[Chapter 5 : Dispoable, Single, Maybe and Completable concepts](Doc5_SpeicifcObservables.md)  
[Chapter 7 : Observables's Transformer Operators](Doc7_ObservableTransformerOperator.md)  
[TableOfContent](index.md)

[Chapter 6: Observable operators]: #chapter-6-observable-operators





