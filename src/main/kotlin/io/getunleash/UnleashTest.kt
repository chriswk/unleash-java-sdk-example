package io.getunleash

import no.finn.unleash.DefaultUnleash
import no.finn.unleash.Unleash
import no.finn.unleash.UnleashException
import no.finn.unleash.event.ToggleEvaluated
import no.finn.unleash.event.UnleashEvent
import no.finn.unleash.event.UnleashReady
import no.finn.unleash.event.UnleashSubscriber
import no.finn.unleash.metric.ClientMetrics
import no.finn.unleash.metric.ClientRegistration
import no.finn.unleash.repository.FeatureToggleResponse
import no.finn.unleash.repository.ToggleCollection
import no.finn.unleash.util.UnleashConfig
import org.apache.logging.log4j.LogManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

@SpringBootApplication
class UnleashTest {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(UnleashTest::class.java)
    }
    @Bean
    fun unleashConfig(): UnleashConfig {
        return UnleashConfig.builder()
            .appName("unleash-tester")
            .unleashAPI("https://app.unleash-hosted.com/demo/api")
            .customHttpHeader("Authorization", "943ca9171e2c884c545c5d82417a655fb77cec970cc3b78a8ff87f4406b495d0")
            .fetchTogglesInterval(15)
            .sendMetricsInterval(360)
            .subscriber(object: UnleashSubscriber {
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
        return DefaultUnleash(unleashConfig, WithinTimeRange());
    }
}