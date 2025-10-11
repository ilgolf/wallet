package me.golf.app.domain.wallet.subscriber

import me.golf.app.domain.wallet.dto.WalletSaveCompleteReplyEvent
import me.golf.core.domain.order.model.Order
import me.golf.core.domain.order.repository.OrderRepository
import me.golf.core.domain.wallet.sender.ReplyMessageSender
import me.golf.core.domain.wallet.sender.payload.WalletSaveReplyPayload
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.util.UUID

@Component
class WalletReplySubscriber(
    private val replyMessageSender: ReplyMessageSender,
    private val orderRepository: OrderRepository
) {

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleWalletSaveCompleteReply(reply: WalletSaveCompleteReplyEvent) {
        val traceId: String = UUID.randomUUID().toString()

        log.info("정산 정보 저장 완료! reply 이벤트 발행 orderId: {}, traceId: {}", reply.orderId, traceId)

        orderRepository.getById(reply.orderId)
            .also { replyMessageSender.send(it.toPayload(traceId)) }
    }

    private fun Order.toPayload(traceId: String): WalletSaveReplyPayload {
        return WalletSaveReplyPayload(
            this.id,
            traceId,
        )
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(WalletReplySubscriber::class.java)
    }
}