package me.golf.infra.domain.wallet.client

import me.golf.infra.domain.wallet.client.dto.ChangeType
import me.golf.infra.domain.wallet.client.dto.SendWalletEventFailCarrier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

interface PaymentClient {
    fun sendWalletEventFailMessage(userId: Long, orderId: String)
}

@Component
internal class PaymentClientImpl(
    private val paymentClient: RestClient,
    @Value("\${payment.endpoint.update_event}") private val endPoint: String
): PaymentClient {

    override fun sendWalletEventFailMessage(userId: Long, orderId: String) {
        val payload = SendWalletEventFailCarrier(
            userId = userId,
            orderId = orderId,
            changeType = ChangeType.FAILURE
        )

        paymentClient.put()
            .uri(endPoint)
            .body(payload)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw IllegalArgumentException("클라이언트 오류: $response")
            }
            .onStatus(HttpStatusCode::is5xxServerError) { _, response ->
                throw IllegalArgumentException("서버 에러: $response")
            }
            .toBodilessEntity()
    }
}
