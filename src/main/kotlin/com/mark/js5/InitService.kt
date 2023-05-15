package com.mark.js5

import io.netty.channel.ChannelHandlerContext
import org.jire.netrune.endpoint.AbstractService
import org.jire.netrune.endpoint.IncomingMessage
import org.jire.netrune.endpoint.Session
import org.jire.netrune.endpoint.init.incoming.InitJs5
import org.jire.netrune.endpoint.init.incoming.InitJs5RequestDecoder
import org.jire.netrune.endpoint.init.incoming.InitLogin
import org.jire.netrune.endpoint.init.incoming.InitLoginDecoder
import org.jire.netrune.endpoint.init.outgoing.*
import org.jire.netrune.endpoint.js5.Js5GroupRepository
import org.jire.netrune.endpoint.js5.Js5Service
import org.jire.netrune.endpoint.login.LoginService
import org.openrs2.crypto.secureRandom
import java.util.concurrent.Executor

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