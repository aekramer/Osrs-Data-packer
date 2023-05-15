package com.mark.js5

import io.netty.channel.ChannelHandlerContext
import com.mark.netrune.endpoint.AbstractService
import com.mark.netrune.endpoint.IncomingMessage
import com.mark.netrune.endpoint.Session
import com.mark.netrune.endpoint.init.incoming.InitJs5
import com.mark.netrune.endpoint.init.incoming.InitJs5RequestDecoder
import com.mark.netrune.endpoint.init.outgoing.*
import com.mark.netrune.endpoint.js5.Js5GroupRepository
import com.mark.netrune.endpoint.js5.Js5Service

class InitService(
    private val js5GroupRepository: Js5GroupRepository
) : AbstractService(32) {

    override fun handle(session: Session, ctx: ChannelHandlerContext, message: IncomingMessage) {
        when (message) {

            is InitJs5 -> {
                // TODO: version verification

                ctx.writeAndFlush(InitJs5Response.Proceed).addListener { future ->
                    if (future.isSuccess) {
                        session.service = Js5Service(js5GroupRepository)
                        ctx.read()
                        ctx.channel().config().isAutoRead = true
                    }
                }
            }

            else -> throw UnsupportedOperationException("Unsupported message: $message")
        }
    }

    init {

        /* JS5 */
        setDecoder(15, InitJs5RequestDecoder)
        setEncoder(InitJs5Response.Proceed::class, Js5ProceedResponseEncoder)
        setEncoder(InitJs5Response.ClientOutOfDate::class, Js5ClientOutOfDateResponseEncoder)
        setEncoder(InitJs5Response.ServerFull::class, Js5ServerFullResponseEncoder)
        setEncoder(InitJs5Response.IpLimit::class, Js5IpLimitResponseEncoder)
    }

}