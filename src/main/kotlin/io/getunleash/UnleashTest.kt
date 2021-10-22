package io.getunleash

import io.getunleash.event.ToggleEvaluated
import io.getunleash.event.UnleashReady
import io.getunleash.event.UnleashSubscriber
import io.getunleash.repository.FeatureToggleResponse
import io.getunleash.util.UnleashConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import java.util.UUID

@SpringBootApplication
class UnleashTest {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(UnleashTest::class.java)
    }

    @Bean
    fun unleashConfig(): UnleashConfig {
        return UnleashConfig.builder()
            .appName("unleash-tester")
            .instanceId(UUID.randomUUID().toString())
            .unleashAPI(getEnv("UNLEASH_API_URL", "http://localhost:4242/api"))
            .customHttpHeader(
                "Authorization",
                getEnv("UNLEASH_API_KEY", "fancy:default.105edea593f2515b57bbbcbc623024988fd746d03cea5bd70d35f65d")
            )
            .fetchTogglesInterval(5)
            .sendMetricsInterval(60)
            .subscriber(object : UnleashSubscriber {
                override fun onReady(unleashReady: UnleashReady) {
                    logger.info("Unleash is ready")
                }

                override fun toggleEvaluated(toggleEvaluated: ToggleEvaluated) {
                    logger.info("Toggle evaluated: ${toggleEvaluated.toggleName} - was enabled: ${toggleEvaluated.isEnabled}")
                }

                override fun togglesFetched(toggleResponse: FeatureToggleResponse) {
                    logger.info("Fetched toggles. Size: ${toggleResponse.toggleCollection.features.size}")
                    super.togglesFetched(toggleResponse)
                }
            })
            .build()
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun unleash(unleashConfig: UnleashConfig): Unleash {
        return DefaultUnleash(unleashConfig)
    }

    fun getEnv(envName: String, defaultValue: String): String {
        return System.getenv(envName) ?: System.getProperty(envName) ?: defaultValue
    }
}
