# Chapter 8: Observable's collection transformers operators
We will see the following operators on Observables:
- toList
- toSortedList
- toMap
- toMultiMap
- collect

They help to transform the stream emitted in a collection, mainly list and map.
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

## toList
Convert your emitted items into a list that is emitted when onComplete occurs. You can provide or not the expected list size.

```java
    /**
     * Using the observableSrc return the list of the days of the week
     * OutPut result:
     * value is [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday]
     */
    public static Single<List<String>> getObservableToList() {
        return observableSrc.toList();
    }
    /**
     * Using the observableSrc return the list of the days of the week
     * OutPut result:
     * value is [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday]
     */
    public static Single<List<String>> getObservableToList() {
        return observableSrc.toList(7);//with the expected list size
    }
```

## toSortedList
Convert your emitted items into a list that is emitted when onComplete occurs. You can provide or not the expected list size.  
You have to provide the comparator (or the default one will be used if it exists)

```java
    /**
     * Using the observableSrc return the list of the days of the week sorted by name's length
     * OutPut result:
     * value is [Monday, Friday, Sunday, Tuesday, Thursday, Saturday, Wednesday]
     */
    public static Single<List<String>> getObservableToSortedList() {
        return observableSrc.toSortedList((item1,item2)->Integer.compare(item1.length(),item2.length()) ,7);
    }
```

## toMap
toMap help you having a map which is <key,Item>
Convert your emitted items into a map that is emitted when onComplete occurs.  
You can provide for each items only its key, the value will be the item itself.  
You can ALSO provide for each items its key and its value  
** /!\Important fact/!\ **  
If an item has the same key as a previous item, the item will override the previous one in the map.  
So your map is <key, item> where you have only on item for a key

```java
    /**
     * Using the observableSrc return the list of the days of the week "grouped" by name's length
     * OutPut result:
     * value is {6=Sunday, 7=Tuesday, 8=Saturday, 9=Wednesday}
     */
    public static Single<Map<Integer,String>> getObservableToMap() {
        return observableSrc.toMap(item->item.length()/* This is the map's key of the item*/);
    }
    
    /**
     * Using the observableSrc return the list of the days of the week grouped by name's length and  
     * the value is the first 3 letters of the day
     * OutPut result:
     * value is {6=Sun, 7=Tue, 8=Sat, 9=Wed}
     */
    public static Single<Map<Integer,String>> getObservableToMapWithKeyValue() {
        return observableSrc.toMap(item->item.length()/* This is the map's key of the item*/,
                item->item.substring(0,3)/* This is the map's value of the item*/);
    }

```

## toMultiMap
toMultiMap help you having a map which is <key,List < Item > >  
Convert your emitted items into a map that is emitted when onComplete occurs.  
You can provide for each items only its key, the value will be the item itself.  
You can ALSO provide for each items its key and its value  

** /!\Important fact/!\ **  
If an item has the same key as a previous item, the item will be added in the list associated with this key.  
So your map is <key,List < Item > > where you gather all the items with the same key in a list

```java
    /**
     * Using the observableSrc return the list of the days of the week sorted by name's length
     * OutPut result:
     * value is {6=[Mon, Fri, Sun], 7=[Tue], 8=[Thu, Sat], 9=[Wed]}
     */
    public static Single<Map<Integer, Collection<String>>> getObservableToMultiMapWithKeyValue() {
        return observableSrc.toMultimap(item->item.length()/* This is the map's key of the item*/,
                item->item.substring(0,3)/* This is the map's value of the item*/);
    }

```


## Collect
collect is a more generic operator which let you manage exactly the collection and how to fill it.  
You need to provide the constructor of your collection and the way to add your item in your collection.

```java
    /**
     * Using the observableSrc and collect return the list myList filled with emitted items
     * OutPut result:
     * value is [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday]
     */
    public static Single<List<String>> getObservableCollect(ArrayList<String> myList) {
        return observableSrc.collect(
                ()->myList,//Constructor
                (list,item)->list.add((String) item)//way to manage each items
                );
    }
```
So, we can use collect to code the toMultiMap operator and it's a good example to understand the power of collect:

```java
    /**
     * Using the observableSrc and collect return the same map as the one obtain by ToMultiMap
     * Use collect to code toMultiMap
     * OutPut result:
     * value is {6=[Monday, Friday, Sunday], 7=[Tuesday], 8=[Thursday, Saturday], 9=[Wednesday]}     
     */
    public static Single<HashMap<Integer,List<String>>> getObservableCollectAsMultiMap() {
        return observableSrc.collect(
                ()->new HashMap<Integer,List<String>>(),//Constructor
                (map,item)->{
                    List valueList=map.get(item.length());
                    if(valueList==null){
                        valueList=new ArrayList();
                        valueList.add(item);
                        map.put(item.length(),valueList);
                    }else{
                        valueList.add(item);
                    }
                }//way to manage each items
        );
    }
```



[Chapter 7 : Observables's Transformer Operators](Doc7_ObservableTransformerOperator.md)  
[Chapter 9: Observable's action operators](Doc9_ObservableDoOnOperator.md)  
[TableOfContent](index.md)





