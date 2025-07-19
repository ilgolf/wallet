package me.golf.infra.domain.order.repository

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.order.repository.OrderRepository
import me.golf.infra.domain.order.dao.OrderJapDao
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderJpaDao: OrderJapDao
): OrderRepository {

    override fun getById(id: String): Order {
        return orderJpaDao.findByIdOrNull(id)?: throw IllegalArgumentException("Order with id '$id' not found")
    }
}