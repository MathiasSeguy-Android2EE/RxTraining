This chapter is a small part of a biggest training project, you can find on [Github](https://github.com/MathiasSeguy-Android2EE/RxTraining).
You'll have the main branch with the answers/responses/unit tests and documentation. You could start with the "questions" branch to only have the questions
You'll have the full table of content of the articles on [Medium](https://medium.com/@android2ee)
You can download the book on [Android2ee](https://www.android2ee.com/)

# Chapter 1: Observable
In this chapter we create Observables in a simple way.  
By the way the rule to observe them is:  
 **observable.subscribe(observer);**  
 But let's first create an observable that we could later observe...

## create()
Your just need to provide a lambda where the parameter is the emiter and
call onNext or onComplete on it:  
```java
        Observable<String> toto= Observable.create(
                emiter -> {
                    emiter.onNext("Monday");
                    emiter.onNext("Tuesday");
                    emiter.onNext("Wednesday");
                    emiter.onNext("Thursday");
                    emiter.onNext("Friday");
                    emiter.onNext("Saturday");
                    emiter.onNext("Sunday");
                    emiter.onComplete();
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

[Chapter 2 : Observers](Doc2_Observer.md)
[TableOfContent](index.md)