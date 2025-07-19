package me.golf.infra.domain.order.dao

import me.golf.core.domain.order.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJapDao: JpaRepository<Order, String>