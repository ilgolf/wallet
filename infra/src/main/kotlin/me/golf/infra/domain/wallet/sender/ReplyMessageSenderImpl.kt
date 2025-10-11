package me.golf.infra.domain.wallet.sender

import com.fasterxml.jackson.databind.ObjectMapper
import me.golf.core.domain.wallet.sender.ReplyMessageSender
import me.golf.core.domain.wallet.sender.payload.WalletSaveReplyPayload
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class ReplyMessageKafkaSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${kafka.topics.wallet-reply}") private val walletReplyTopic: String
): ReplyMessageSender {

    override fun send(payload: WalletSaveReplyPayload) {
        kotlin.runCatching {
            kafkaTemplate.send(walletReplyTopic, payload.toJson())
            log.info("✅ Kafka 성공 orderId {}, tracedId: {}", payload.orderId, payload.traceId)
        }.onFailure { ex ->
            log.error("❌wallet reply 이벤트 처리 실패 orderId: {}, traceId: {}", payload.orderId, payload.traceId, ex)
            throw ex
        }
    }

    private fun WalletSaveReplyPayload.toJson(): String = objectMapper.writeValueAsString(this)

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReplyMessageKafkaSender::class.java)
    }
}

@Component
@Profile("test")
class ReplyMessageDefaultSender: ReplyMessageSender {

    override fun send(payload: WalletSaveReplyPayload) {
        log.info("reply message 이벤트 발생: {}", payload.orderId)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReplyMessageDefaultSender::class.java)
    }
}