package me.golf.core.domain.wallet.repository

import me.golf.core.domain.order.model.Order
import me.golf.core.domain.wallet.model.Wallet
import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand

interface WalletRepository {

    fun save(wallet: Wallet)
    fun existsByOrderId(order: Order): Boolean
    fun updateWalletEventFail(command: WalletSaveCommand)
}