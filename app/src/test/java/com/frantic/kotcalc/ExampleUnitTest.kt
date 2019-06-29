package com.frantic.kotcalc

import android.util.Log
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun takeDoubleWithoutTail_isCorrect(){
        val activity = MainActivity()
        val a:Double = 0.1
        val b:Double = 0.2
        val c:Double = a + b
        assertEquals("0.3", activity.takeDoubleWithoutTail(c))
    }
    @Test
    fun calcResult_isCorrect(){
        val activity = MainActivity()
        activity.result = 1.0
        activity.num.append("2")
        activity.lastOperation.append("+")
        activity.calcResult()
        assertEquals(3.0, activity.result,0.0000000001)

        activity.result = 2.0
        activity.num.clear()
        activity.num.append("2")
        activity.lastOperation.clear()
        activity.lastOperation.append("*")
        activity.calcResult()
        assertEquals(4.0, activity.result,0.0000000001)

        activity.result = 4.0
        activity.num.clear()
        activity.num.append("2")
        activity.lastOperation.clear()
        activity.lastOperation.append("/")
        activity.calcResult()
        assertEquals(4.0, activity.result,0.0000000001)
    }
}
