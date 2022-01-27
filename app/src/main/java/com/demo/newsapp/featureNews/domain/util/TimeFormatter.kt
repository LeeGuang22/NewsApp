package com.demo.newsapp.featureNews.domain.util

import org.threeten.bp.Period

fun format(period: Period): String {
    return when {
        period.years > 0 -> {
            val dateFormat = if (period.years < 1) "year" else "years"
            formatTimeString(period.years, dateFormat)
        }
        period.months > 0 -> {
            val dateFormat = if (period.months < 1) "month" else "months"
            formatTimeString(period.months, dateFormat)
        }
        period.days / 7 < 1 -> {
            val dateFormat = if (period.days < 1) "day" else "days"
            formatTimeString(period.days, dateFormat)
        }
        else -> {
            val dateFormat = if ((period.days / 7) < 1) "week" else "weeks"
            formatTimeString(period.days / 7, dateFormat)
        }
    }
}

fun formatTimeString(period: Int, dateFormatString: String): String {
    return "$period $dateFormatString ago"
}