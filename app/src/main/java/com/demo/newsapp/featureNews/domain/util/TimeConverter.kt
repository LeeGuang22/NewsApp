package com.demo.newsapp.featureNews.domain.util

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.ZoneId

/**
 * Convert timeCreated to time ago
 *
 * @param timeCreated Long
 * @return String of formatted value
 */
fun convertTime(timeCreated: Long): String {
    val period = Period.between(
        Instant.ofEpochSecond(timeCreated).atZone(ZoneId.systemDefault()).toLocalDate(),
        LocalDate.now()
    )
    return format(period)
}