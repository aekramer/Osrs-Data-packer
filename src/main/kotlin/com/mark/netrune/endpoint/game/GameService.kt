package com.mark.netrune.endpoint.game

import io.netty.channel.ChannelHandlerContext
import com.mark.netrune.endpoint.AbstractService
import com.mark.netrune.endpoint.IncomingMessage
import com.mark.netrune.endpoint.Session

class GameService : AbstractService(256) {

    override fun handle(session: Session, ctx: ChannelHandlerContext, message: IncomingMessage) {

    }

    init {
        // TODO
    }

}