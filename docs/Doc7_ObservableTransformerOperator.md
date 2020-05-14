# [Chapter 7: Observable transformers operators]
We will see the following operators on Observables:
- map (and flatMap)
- cast
- startWith
- defaultIfEmpty
- switchIfEmpty
- sorted
- delay
- repeat
- scan
- count
- reduce
- all
- any
- contains



## map (and flatMap)
map: Convert the Observable<T> into an Observable<R> using a lambda Function<T,R>. One item of T type will be converted into one item of R type. It's one to one relation.
flatMap: Convert every item of the Observable<T> into an Observable<R>. So the lambda Function is <T,Observable<R>> and you ends up with a merge of Observable<R>. It's a one to many relation. We will see this notion later.

So using map you can convert a specific stream of object T into another stream of object R.
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
    private static Map<String, String> morse = new HashMap<>();//filled elsewhere
  */ /**
     * Using the observableSrc return the associated morse code
     * OutPut result:
     * value is ------.-...--.--
     * value is -..-....-...--.--
     * value is .--.-..-.....-...--.--
     * value is -......-.-....-...--.--
     * value is ..-..-...-...--.--
     * value is ....--..-.-.-...--.--
     * value is .....--.-...--.--
     * OnComplete is called
     */
    public static Observable<String> getObservableMap() {
        return observableSrc.map(item->item.toLowerCase().toCharArray())
                .map(chars -> {
                    StringBuffer strBuf=new StringBuffer();
                    for (char aChar : chars) {
                        strBuf.append(morse.get(Character.toString(aChar)));
                    }
                    return strBuf.toString();
                });
    }
```

## cast
It helps to cast the Observable<R> to Observable<T>.  
Mainly it's usefull when you have a mother class (or interface) and cast all the elements into this class.

## startWith
It helps to start the emission with a specific item.

```java
    /**
     *  Using the observableSrc return a stream that starts with "Days of the week are: "
     * OutPut result:
     * value is Days of the week are:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    public static Observable<String> getObservableStartWith() {
        return observableSrc.startWith("Days of the week are: ");
    }
```
## defaultIfEmpty and switchIfEmpty
It helps to emits an element if the original stream is empty.
defaultIfEmpty: Helps you emits a specific item if the stream is empty; onComplete is called en no item has been emitted.  
switchIfEmpty: Helps you switch to an other observable if the stream is empty; onComplete is called en no item has been emitted.
### defaultIfEmpty
Simple example on how to use defaultIfEmpty.Has to wait the onComplete.
```java
    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found return "empty"
     */
    public static Observable<String> getObservableDefaultIfEmpty(String firstLetter) {
        return observableSrc.filter(item -> item.toLowerCase().charAt(0)==firstLetter.toLowerCase().charAt(0))
                .defaultIfEmpty("empty");
    }
    
    /**
     * OutPut result:
     * value is Monday
     * OnComplete is called
     * value is empty
     * OnComplete is called
     */
    @Test
    public void testDefault() {
        Answer7_ObservableTransformerOpertaor.getObservableDefaultIfEmpty("M")
                .subscribe(value -> {System.out.println("value is " +value);});
        Answer7_ObservableTransformerOpertaor.getObservableDefaultIfEmpty("L")
                .subscribe(value -> {System.out.println("value is " +value);});
    }
```
### switchIfEmpty
Simple example on how to use switchIfEmpty by returning a new streams of items. Has to wait the onComplete.

```java
    
    /**
     * Using the observableSrc return a stream that returns elements that start with firstLetter
     * if no elements found  add the firstLetter to the days of the week and return them
     */
    public static Observable<String> getObservableSwitchIfEmpty(String firstLetter) {
        return observableSrc.filter(item -> item.toLowerCase().charAt(0)==firstLetter.toLowerCase().charAt(0))
                .switchIfEmpty(observableSrc.map(item -> firstLetter+item));
    }
    
    /**
     * OutPut result:
     * value is Monday
     * OnComplete is called
     * value is LMonday
     * value is LTuesday
     * value is LWednesday
     * value is LThursday
     * value is LFriday
     * value is LSaturday
     * value is LSunday
     * OnComplete is called
     */
    @Test
    public void testDefault() {
        Answer7_ObservableTransformerOpertaor.getObservableDefaultIfEmpty("M")
                .subscribe(value -> {System.out.println("value is " +value);});
        Answer7_ObservableTransformerOpertaor.getObservableDefaultIfEmpty("L")
                .subscribe(value -> {System.out.println("value is " +value);});
    }
```


## sorted
It helps you sorts the items emitted. Has to wait the onComplete. Sorted objects need to implement comparable or you can provide it.

```java
    /**
     *  Using the observableSrc return a stream that starts with "Days of the week are: "
     * OutPut result:
     * value is Days of the week are:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Thursday
     * value is Friday
     * value is Saturday
     * value is Sunday
     * OnComplete is called
     */
    public static Observable<String> getObservableStartWith() {
        return observableSrc.startWith("Days of the week are: ");
    }
```






[Chapter 5 : Dispoable, Single, Maybe and Completable concepts](Doc5_SpeicifcObservables.md)  
[Chapter 4 : Observables Basics Creators](Doc4_ObservableCreators.md)  
[TableOfContent](index.md)


[Chapter 7: Observable transformers operators]: #chapter-7-observable-transformers-operators



