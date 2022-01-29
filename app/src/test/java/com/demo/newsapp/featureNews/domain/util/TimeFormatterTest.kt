package com.demo.newsapp.featureNews.domain.util

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.Period

class TimeFormatterTest {

    @Test
    fun testFormatToString_whenPeriodIsOneYear_shouldReturnCorrectValue() {
        val period = Period.ofYears(1)
        assertEquals("1 year ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsMoreThanOneYear_shouldReturnCorrectValue() {
        val period = Period.ofYears(10)
        assertEquals("10 years ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsOneMonth_shouldReturnCorrectValue() {
        val period = Period.ofMonths(1)
        assertEquals("1 month ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsMoreThanOneMonth_shouldReturnCorrectValue() {
        val period = Period.ofMonths(15)
        assertEquals("15 months ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsOneDay_shouldReturnCorrectValue() {
        val period = Period.ofDays(1)
        assertEquals("1 day ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsMoreThanOneDay_shouldReturnCorrectValue() {
        val period = Period.ofDays(5)
        assertEquals("5 days ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsMoreThanSevenDays_shouldReturnCorrectValue() {
        val period = Period.ofDays(8)
        assertEquals("1 week ago", formatToString(period))
    }

    @Test
    fun testFormatToString_whenPeriodIsOneWeek_shouldReturnCorrectValue() {
        val period = Period.ofWeeks(1)
        assertEquals("1 week ago", formatToString(period))
    }
}