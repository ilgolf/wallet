package me.golf.infra.domain.order.repository

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.order.repository.OrderRepository
import me.golf.core.domain.seller.model.SettlementSummary
import me.golf.infra.domain.order.dao.OrderJapDao
import me.golf.infra.domain.order.dao.OrderJdslDao
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderJpaDao: OrderJapDao,
    private val orderJdslDao: OrderJdslDao,
): OrderRepository {

    override fun getById(id: String): Order {
        return orderJpaDao.findByIdOrNull(id)?: throw IllegalArgumentException("Order with id '$id' not found")
    }

    override fun findSellersByOrderId(id: String): List<me.golf.core.domain.seller.model.Seller> {
        return orderJdslDao.findSellersByOrderId(id)
    }

    override fun findSummaryBySearch(settlementId: Long?, page: Int, limit: Int): Page<SettlementSummary> {
        return orderJdslDao.findSettlementSummary(
            settlementId = settlementId,
            page = page,
            limit = limit,
        )
    }
}
