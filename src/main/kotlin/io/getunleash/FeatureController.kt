package io.getunleash

import io.getunleash.Unleash
import org.springframework.http.HttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FeatureController(val unleashClient: Unleash) {

    @GetMapping("/feature/{featureName}")
    fun checkFeature(@PathVariable("featureName") featureName: String, @RequestParam params: Map<String, String>): Boolean {
        val ctx = params.entries.fold(UnleashContext.builder()) { context, param ->
            context.addProperty(param.key, param.value)
        }
        return unleashClient.isEnabled(featureName, ctx.build());
    }

}
