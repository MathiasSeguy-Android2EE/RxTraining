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
 /**
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
Mainly it's useful when you have a mother class (or interface) and cast all the elements into this class.

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
defaultIfEmpty: Helps you emits a specific item if the stream is empty; onComplete is called even if no item has been emitted.  
switchIfEmpty: Helps you switch to an other observable if the stream is empty; onComplete is called even if no item has been emitted.
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
     * Using the observableSrc return a stream ordered by alphabetic order
     * OutPut result Sorted:
     * value is Friday
     * value is Monday
     * value is Saturday
     * value is Sunday
     * value is Thursday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
    public static Observable<String> getObservableSorted() {
        return observableSrc.sorted();
    }

    /**
     * Using the observableSrc return a stream reverse ordered by alphabetic order
     * OutPut result SortedReverse:
     * value is Wednesday
     * value is Tuesday
     * value is Thursday
     * value is Sunday
     * value is Saturday
     * value is Monday
     * value is Friday
     * OnComplete is called
     */
    public static Observable<String> getObservableSortedReverse() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return observableSrc.sorted(Comparator.reverseOrder());
        } else {
            return observableSrc.sorted((item1, item2) -> -1 * (item1.compareTo(item2)));
        }
    }

    /**
     *  Using the observableSrc return a stream ordered by words's length
     * OutPut result SortedByLength:
     * value is Monday
     * value is Friday
     * value is Sunday
     * value is Tuesday
     * value is Thursday
     * value is Saturday
     * value is Wednesday
     * OnComplete is called
     */
    public static Observable<String> getObservableSortedByLength() {
        return observableSrc.sorted((item1, item2) -> Integer.compare(item1.length(), item2.length()));
    }
```


## delay
Delay: It helps you delay the emission of the items.  
By default the delay applies only on the emission of the items, the errors are immediately fired. but you can also specify if error needs to have a delay.

```java
    /**
     *  Using the observableSrc return a stream ordered by words's length
     */
    public static Observable<String> getObservableDelayed() {
        return observableSrc.delay(3,TimeUnit.SECONDS,/* Time duration of the delay*/
         Schedulers.computation()/*scheduler to use*/,
         false/*boolean delay Error*/);
    }
```


## repeat
Repeat: It helps you delay the emission of the items.  
By default the delay applies only on the emission of the items, the errors are immediately fired. but you can also specify if error needs to have a delay.

```java
   
    /**
     *  Using the observableSrc return twice the 3 first days of the week week
     * OutPut result:
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * value is Monday
     * value is Tuesday
     * value is Wednesday
     * OnComplete is called
     */
     */
    public static Observable<String> getObservableRepeat() {
        return observableSrc.take(3).repeat(2);
    }
```


## scan
Scan: It's a reducing operator.  
It's based on a lambda function which take the item emitted and concatenate it with an accumulator:(accumulator,next)->accumulator + next.  
The accumulator is emitted for every item received.  
The accumulator is initialized with the first item.  
You can provide an initial value, it will be emitted first and used for the accumulation. This is the trick to change the item emitted type.
```java
   
    /**
     *  Using the observableSrc test scan
     * OutPut result:     *
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * value is Monday
     * OnComplete is called
     */
    public static Observable<String> getObservableScanStupid() {
        return observableSrc.scan((accumulator,next)->accumulator);
    }


    /**
     *  Using the observableSrc return the list of the days of the week split by a ','
     * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
     * OnComplete is called
     */
    public static Observable<String> getObservableScan() {
        return observableSrc.scan((accumulator,next)->accumulator+", "+next ).takeLast(1);
    }


    /**
     *  Using the observableSrc return the accumulate length of each days
     * OutPut result:
     * value is 0
     * value is 6
     * value is 13
     * value is 22
     * value is 30
     * value is 36
     * value is 44
     * value is 50
     * OnComplete is called
     */
    public static Observable<Integer> getObservableScanWithInitializer() {
        return observableSrc.scan(0,(accumulator,next)->accumulator+next.length() );
    }
```


## reduce
Reduce: It helps you accumulate the item into an accumulator which will be emitted when onComplete is reached on the initial stream.  
reduce==scan().takeLast(1)  
Reduce returns a Single or MayBE.  
It's based on a lambda function which take the item emitted and concatenate it with an accumulator:(accumulator,next)->accumulator + next.  
The accumulator is emitted when the last item is received, it needs to wait the onComplete.  
The accumulator is initialized with the first item, if no initial value is provided.  
You can provide an initial value, it will be used for the accumulation. This is the trick to change the item emitted type.

```java
   
    /**
     *  Using the observableSrc return the list of the day split by a coma
     * OutPut result:
     * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
     * OnComplete is called
     */
    public static Maybe<String> getObservableReduce() {
        return observableSrc.reduce((accumulator,next)->accumulator+", "+next );
    }
    /**
     *  Using the observableSrc return the accumulate length of each days
     * OutPut result:
     * value is 50
     * OnComplete is called
     */
    public static Single<Integer> getObservableReduceWithInitializer() {
        return observableSrc.reduce(0,(accumulator,next)->accumulator+next.length() );
    }
```
## count
count: It returns the number of emitted elements. It has to wait the onComplete to fire it.  
count returns a Single<Long>.

```java
       /**
     *  Using the observableSrc return the number of days in a week
     * OutPut result:
     * value is 7
     */
    public static Single<Long> getObservableCount() {
        return observableSrc.count();
    }
```

## all,any,contains
all: check a condition for every item, if each condition is true, the return is true else it will be false. The emission is done when a condition is calculated as false or when the onComplete is reached(and return true).  
any: check a condition for every item, if one condition is true, the return is true else it will be false.The emission is done when a condition is calculated as true or when the onComplete is reached(and return false).  
contains: check if the emitted item is the one expected, when the expected item is found, it immediately returns true else it wait onComplete (and return false)  
all is a Single<Boolean>  
any is a Single<Boolean>  
contains is a Single<Boolean>

```java
 /**
     *  Using the observableSrc return true if all the day are called Wednesday
     *  return false
     */
    public static Single<Boolean> getObservableAll() {
        return observableSrc.all(item-> item.equals("Wednesday"));
    }


    /**
     *  Using the observableSrc return true if there is a day called Wednesday
     *  return true
     */
    public static Single<Boolean> getObservableAny() {
        return observableSrc.any(item-> item.equals("Wednesday"));
    }


    /**
     *  Using the observableSrc return true if the items emitted contains Wednesday
     *  return true
     */
    public static Single<Boolean> getObservableContains() {
        return observableSrc.contains("Wednesday");
    }
```

[Chapter 6: Observable operators](Doc6_ObservableOpertaor.md)  
[Chapter 8: Observable Collection operators](Doc8_ObservableCollectionOperator.md)  
[TableOfContent](index.md)


[Chapter 7: Observable transformers operators]: #chapter-7-observable-transformers-operators



