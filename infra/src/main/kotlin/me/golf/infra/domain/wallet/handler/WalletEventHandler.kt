package me.golf.infra.domain.wallet.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.golf.core.domain.wallet.usecase.WalletUseCase
import me.golf.infra.config.KafkaRetryableWithDLT
import me.golf.infra.domain.wallet.converter.toCommand
import me.golf.infra.domain.wallet.handler.dto.WalletMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

interface WalletEventHandler {
    fun handleSaveSettlement(message: String, acknowledgment: Acknowledgment)
}

@Component
internal class KafkaWalletEventHandler(
    private val objectMapper: ObjectMapper,
    private val walletUseCase: WalletUseCase
) : WalletEventHandler {

    private val log: Logger = LoggerFactory.getLogger(KafkaWalletEventHandler::class.java)

    @KafkaRetryableWithDLT
    @KafkaListener(
        topics = [WALLET_TOPIC],
        groupId = "wallet-group-id",
        containerFactory = "walletContainerFactory"
    )
    override fun handleSaveSettlement(
        message: String,
        acknowledgment: Acknowledgment
    ) {
        if (message.isBlank()) {
            log.warn("Received empty or blank message, skipping processing")
            return
        }

        kotlin.runCatching { saveSettlementProcess(message, acknowledgment) }
            .onFailure { loggingAndThrow(message, it) }
    }

    private fun saveSettlementProcess(message: String, acknowledgment: Acknowledgment) {
        val walletMessage = objectMapper.readValue<WalletMessage>(message)
        walletUseCase.create(walletMessage.toCommand())
        acknowledgment.acknowledge()
    }

    private fun loggingAndThrow(message: String, it: Throwable): Nothing {
        log.error("메시지 처리 실패: {}", message, it)
        throw it
    }

    companion object {
        private const val WALLET_TOPIC = "wallet"
    }
}