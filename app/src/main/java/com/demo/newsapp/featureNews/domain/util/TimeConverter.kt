package com.demo.newsapp.featureNews.domain.util

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.ZoneId

fun Long.toStringFormat(): String {
    val period = Period.between(
        Instant.ofEpochSecond(this).atZone(ZoneId.systemDefault()).toLocalDate(),
        LocalDate.now()
    )
    return formatToString(period)
}