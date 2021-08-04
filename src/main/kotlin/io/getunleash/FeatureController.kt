package io.getunleash

import no.finn.unleash.Unleash
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FeatureController(val unleashClient: Unleash) {

    @GetMapping("/feature/{featureName}")
    fun checkFeature(@PathVariable("featureName") featureName: String): Boolean {
        return unleashClient.isEnabled(featureName);
    }

    @GetMapping("/daterange")
    fun checkWithin(): Boolean {
        return unleashClient.isEnabled("within.time.range");
    }
}