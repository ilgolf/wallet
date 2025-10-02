package me.golf.infra.domain.order.dao

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import jakarta.persistence.EntityManager
import me.golf.core.domain.order.model.Order
import me.golf.core.domain.orderitem.model.OrderItem
import me.golf.core.domain.seller.model.Seller
import me.golf.core.domain.seller.model.SettlementSummary
import me.golf.core.domain.ticket.model.Ticket
import me.golf.core.domain.wallet.model.Wallet
import me.golf.infra.util.createQuery
import me.golf.infra.util.createQueryCount
import me.golf.infra.util.createQueryList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class OrderJdslDao(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
    private val jpqlRenderer: JpqlRenderer
) {

    fun findSellersByOrderId(orderId: String): List<Seller> {
        val query: SelectQuery<Seller> = jpql {
            select(entity(Seller::class))
                .from(
                    entity(OrderItem::class),
                    join(OrderItem::ticket),
                    join(Ticket::seller)
                )
                .where(
                    path(OrderItem::order)(Order::id).eq(orderId)
                )
        }

        return entityManager.createQueryList(query, jpqlRenderContext, jpqlRenderer)
    }

    fun findSettlementSummary(page: Int, limit: Int): Page<SettlementSummary> {
        val query: SelectQuery<SettlementSummary> = jpql {
            selectNew<SettlementSummary>(
                path(Wallet::id),
                path(Wallet::amount),
                path(Seller::companyName),
                path(Seller::representativeName),
                path(Wallet::fee),
                path(Wallet::settlementStatus),
                path(Wallet::settlementCompleteDate),
                path(Wallet::createdDate)
            )
                .from(
                    entity(Wallet::class),
                    join(Wallet::seller)
                )
        }

        val countQuery = jpql {
            select(count(Wallet::id))
                .from(
                    entity(Wallet::class),
                    join(Wallet::seller)
                )
        }

        return PageImpl(
            entityManager.createQueryList(query, jpqlRenderContext, jpqlRenderer),
            PageRequest.of(page, limit),
            entityManager.createQueryCount<Long>(countQuery, jpqlRenderContext, jpqlRenderer)
        )
    }
}
