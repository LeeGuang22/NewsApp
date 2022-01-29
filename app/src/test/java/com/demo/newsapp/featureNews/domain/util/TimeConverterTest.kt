package com.demo.newsapp.featureNews.domain.util

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeConverterTest {

    @Test
    fun testConvertTime_shouldReturnCorrectValue() {
        val timeCreated = 1530322670L
        assertEquals("3 years ago", timeCreated.toStringFormat())
    }
}