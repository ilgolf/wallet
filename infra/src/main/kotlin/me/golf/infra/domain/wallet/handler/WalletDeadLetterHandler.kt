package me.golf.infra.domain.wallet.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.golf.core.domain.wallet.repository.WalletRepository
import me.golf.infra.domain.wallet.converter.toCommand
import me.golf.infra.domain.wallet.handler.dto.WalletMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

interface WalletDeadLetterHandler {

    fun handleDeadLetter(message: String, acknowledgment: Acknowledgment)
}

@Component
internal class WalletDeadLetterHandlerImpl(
    private val walletRepository: WalletRepository,
    private val objectMapper: ObjectMapper,
): WalletDeadLetterHandler {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["wallet-dlt"],
        groupId = "wallet-dead-letter-group",
        containerFactory = "walletContainerFactory",
    )
    override fun handleDeadLetter(message: String, acknowledgment: Acknowledgment) {
        log.info("kafka event 재처리 실패 Dead Letter 처리: {}", message)

        runCatching { processDeadLetter(message, acknowledgment) }
            .onFailure { log.error("Dead Letter 처리 실패: {}", message, it) }
    }

    private fun processDeadLetter(message: String, acknowledgment: Acknowledgment) {
        val walletMessage = objectMapper.readValue<WalletMessage>(message)

        walletRepository.updateWalletEventFail(walletMessage.toCommand())
        acknowledgment.acknowledge()
    }
}