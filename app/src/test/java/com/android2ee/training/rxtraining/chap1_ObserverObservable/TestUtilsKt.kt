package com.android2ee.training.rxtraining.chap1_ObserverObservable

import org.junit.Assert

// 
/** Created by Mathias Seguy also known as Android2ee on 15/05/2020.
 * The goal of this class is to :
 *
 */
object TestUtilsKt {


    fun assertLongListEquals(receivedItems: List<Long?>, expectedItems: List<Long?>) {
        for (i in expectedItems.indices) {
            Assert.assertEquals(expectedItems[i], receivedItems[i])
        }
    }

    fun assertMapEquals(receivedItems: MutableMap<Any, Any>, expectedItems: MutableMap<Any, Any>): Boolean {
        return receivedItems.equals(expectedItems)
    }
}