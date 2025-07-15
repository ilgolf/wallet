package me.golf.core.domain.wallet.usecase

import me.golf.core.domain.wallet.usecase.command.WalletSaveCommand

interface WalletUseCase {

    fun save(command: WalletSaveCommand)
}