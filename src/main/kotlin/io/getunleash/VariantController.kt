package io.getunleash

import io.getunleash.Unleash
import io.getunleash.Variant
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class VariantController(val unleashClient: Unleash) {

    @GetMapping("/variant/{featureName}")
    fun checkFeature(@PathVariable("featureName") featureName: String): Variant {
        return unleashClient.getVariant(featureName)
    }
}
