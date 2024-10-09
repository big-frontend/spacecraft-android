package com.spacecraft

import com.electrolytej.main.util.BadgeUtils
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        BadgeUtils.setCount(1, null)
        assertEquals(4, 2 + 2)
    }
}