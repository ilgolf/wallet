package me.golf.core.domain.wallet.sender

import me.golf.core.domain.wallet.sender.payload.WalletSaveReplyPayload

interface ReplyMessageSender {

    fun send(payload: WalletSaveReplyPayload)
}