package org.jire.js5server.codec.init

import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.timeout.IdleStateEvent
import mu.KotlinLogging
import org.jire.js5server.Js5GroupRepository
import org.jire.js5server.PipelineConstants.DECODER
import org.jire.js5server.PipelineConstants.ENCODER
import org.jire.js5server.PipelineConstants.HANDLER
import org.jire.js5server.codec.ClientResponse
import org.jire.js5server.codec.js5.Js5Handler
import org.jire.js5server.codec.js5.Js5Decoder

class InitHandler(
    private val version: Int,
    private val groupRepository: Js5GroupRepository
) : SimpleChannelInboundHandler<InitRequest>() {

    private val logger = KotlinLogging.logger {}

    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.read()
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: InitRequest) {
        when (msg) {
            is InitRequest.Js5 -> {
                ctx.pipeline().remove(DECODER)

                if (version == msg.version) {
                    val encoder = ctx.pipeline().get(ENCODER)
                    ctx.writeAndFlush(InitResponse.Js5(ClientResponse.SUCCESSFUL))
                        .addListener { future ->
                            if (future.isSuccess) {
                                ctx.pipeline().remove(encoder)
                                ctx.pipeline().remove(this)

                                ctx.pipeline().addLast(DECODER, Js5Decoder())
                                ctx.pipeline().addLast(HANDLER, Js5Handler(groupRepository))
                            }
                        }
                } else {
                    ctx.writeAndFlush(InitResponse.Js5(ClientResponse.SERVER_UPDATED))
                        .addListener(ChannelFutureListener.CLOSE)
                }
            }

            InitRequest.Rs2 -> ctx.close()
        }
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        if (!ctx.isRemoved)
            ctx.read()
    }

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        if (evt is IdleStateEvent)
            ctx.close()
    }

    @Deprecated("Deprecated in Java")
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        if (logger.isErrorEnabled)
            logger.error("Exception during handshake", cause)
    }

}