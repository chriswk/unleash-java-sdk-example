package io.getunleash

import no.finn.unleash.Constraint
import no.finn.unleash.UnleashContext
import no.finn.unleash.strategy.Strategy
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WithinTimeRange : Strategy {
    val ISO = DateTimeFormatter.ISO_DATE_TIME
    override fun getName(): String {
        return "WithinTimeRange"
    }

    override fun isEnabled(parameters: Map<String, String>): Boolean {
        val startTs = parameters["start"]
        val endTs = parameters["end"]
        val start = ZonedDateTime.parse(startTs, ISO)
        val end = ZonedDateTime.parse(endTs, ISO)
        val now = ZonedDateTime.now()
        return now.isAfter(start) && now.isBefore(end)
    }

    override fun isEnabled(parameters: Map<String, String>, unleashContext: UnleashContext): Boolean {
        return isEnabled(parameters)
    }

    override fun isEnabled(
        parameters: Map<String, String>,
        unleashContext: UnleashContext,
        constraints: List<Constraint>
    ): Boolean {
        return isEnabled(parameters)
    }
}