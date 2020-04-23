# Chapter 1: Observable
Creating an Observable in a simple way can be done using.
By the way the rule is:  
 **observable.subscribe(observer);**  
 But let's first create an observable that we could later observe...

## create()
Your just need to provide a lambda where the parameter is the emiter and
call onNext or onComplete on it:  
```Java
        Observable<String> toto= Observable.create(
                emiter -> {
                    emiter.onNext("Monday");
                    emiter.onNext("Tuesday");
                    emiter.onNext("Wednesday");
                    emiter.onNext("Thursday");
                    emiter.onNext("Friday");
                    emiter.onNext("Saturday");
                    emiter.onNext("Sunday");
                }
```
## just()
Your just need to provide the element or the list of elements to emit:
```java
        Observable<String> toto= Observable.just(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
```

## fromItreable()
Your just need to provide the list of elements to emit:
```java
        List<String> weekDays= Arrays.asList(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        return Observable.fromIterable(weekDays);
```
