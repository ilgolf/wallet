package me.golf.core.domain.order.repository

import me.golf.core.domain.order.model.Order

interface OrderRepository {
    fun getById(id: String): Order
}