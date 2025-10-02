package me.golf.core.domain.order.repository

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.seller.model.Seller
import me.golf.core.domain.seller.model.SettlementSummary
import org.springframework.data.domain.Page

interface OrderRepository {
    fun getById(id: String): Order
    fun findSellersByOrderId(id: String): List<Seller>
    fun findSummaryBySearch(page: Int, limit: Int): Page<SettlementSummary>
}
