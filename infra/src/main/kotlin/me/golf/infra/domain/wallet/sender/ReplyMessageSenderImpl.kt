package me.golf.infra.domain.wallet.sender

import com.fasterxml.jackson.databind.ObjectMapper
import me.golf.core.domain.wallet.sender.ReplyMessageSender
import me.golf.core.domain.wallet.sender.payload.WalletSaveReplyPayload
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class ReplyMessageKafkaSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${kafka.topics.wallet-reply}") private val walletReplyTopic: String
): ReplyMessageSender {

    override fun send(payload: WalletSaveReplyPayload) {
        kafkaTemplate.send(walletReplyTopic, payload.toJson())
            .whenComplete { result, ex ->
                handleKafkaResult(
                    result = result,
                    ex = ex,
                    orderId = payload.orderId,
                    traceId = payload.traceId
                )
            }
    }

    private fun WalletSaveReplyPayload.toJson(): String = objectMapper.writeValueAsString(this)

    private fun handleKafkaResult(
        result: SendResult<String, String>?,
        ex: Throwable?,
        orderId: String,
        traceId: String
    ) {
        if (ex != null) {
            log.error("❌wallet reply 이벤트 처리 실패 orderId: {}, traceId: {}", orderId, traceId, ex)
            return
        }
        log.info("✅ Kafka 성공: ${result?.recordMetadata?.offset()}")
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}

@Component
@Profile("test")
class ReplyMessageDefaultSender: ReplyMessageSender {

    override fun send(payload: WalletSaveReplyPayload) {
        log.info("reply message 이벤트 발생: {}", payload.orderId)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}