package io.getunleash

import no.finn.unleash.DefaultUnleash
import no.finn.unleash.Unleash
import no.finn.unleash.util.UnleashConfig
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

@SpringBootApplication
class UnleashTest {

    @Bean
    fun unleashConfig(): UnleashConfig {
        return UnleashConfig.builder()
            .appName("unleash-tester")
            .unleashAPI("https://app.unleash-hosted.com/demo/api")
            .customHttpHeader("Authorization", "943ca9171e2c884c545c5d82417a655fb77cec970cc3b78a8ff87f4406b495d0")
            .fetchTogglesInterval(15)
            .sendMetricsInterval(360)
            .build()
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun unleash(unleashConfig: UnleashConfig): Unleash {
        return DefaultUnleash(unleashConfig, WithinTimeRange());
    }
}