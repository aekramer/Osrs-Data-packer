package org.jire.js5server

import io.netty.channel.ChannelFuture
import mu.KotlinLogging

class Js5Service(
    bootstrapFactory: BootstrapFactory = Js5ServerBootstrapFactory(Js5ChannelInitializer())
) : AutoCloseable {

    private val logger = KotlinLogging.logger {}

    private val parentGroup = bootstrapFactory.createEventLoopGroup(1)
    private val childGroup = bootstrapFactory.createEventLoopGroup()

    private val bootstrap = bootstrapFactory.createServerBootstrap(parentGroup, childGroup)

    fun bind(port: Int): ChannelFuture {
        logger.debug("Binding to port {} with group type \"{}\"", port, parentGroup::class.java)
        return bootstrap.bind(port)
    }

    fun listen(port: Int) = bind(port).addListener {
        logger.info("Listening on port {}", port)
    }

    override fun close() {
        childGroup.shutdownGracefully()
        parentGroup.shutdownGracefully()
    }

}