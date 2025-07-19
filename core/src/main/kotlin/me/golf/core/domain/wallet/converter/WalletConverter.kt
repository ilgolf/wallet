package me.golf.core.domain.wallet.converter

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.seller.model.Seller
import me.golf.core.domain.wallet.model.SettlementStatus
import me.golf.core.domain.wallet.model.Wallet

fun toWallet (order: Order, seller: Seller): Wallet {
    return Wallet(
        amount = order.amount,
        fee = order.fee(),
        settlementStatus = SettlementStatus.WAIT,
        settlementCompleteDate = null,
        order = order,
        seller = seller,
    )
}