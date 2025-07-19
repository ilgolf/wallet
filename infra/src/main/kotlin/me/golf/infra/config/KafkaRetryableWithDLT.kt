package me.golf.infra.config

import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.retry.annotation.Backoff

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@RetryableTopic(
    attempts = "3",
    backoff = Backoff(delay = 1000L, multiplier = 2.0),
    autoCreateTopics = "false",
    retryTopicSuffix = "-retry",
    dltTopicSuffix = "-dlt",
    dltStrategy = DltStrategy.FAIL_ON_ERROR,
    include = [RuntimeException::class]
)
annotation class KafkaRetryableWithDLT