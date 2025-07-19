package me.golf.infra.domain.wallet.converter

import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand
import me.golf.infra.domain.wallet.handler.dto.WalletMessage

fun WalletMessage.toCommand(): WalletSaveCommand {
    return WalletSaveCommand(
        userId = this.userId,
        orderId = this.orderId,
    )
}