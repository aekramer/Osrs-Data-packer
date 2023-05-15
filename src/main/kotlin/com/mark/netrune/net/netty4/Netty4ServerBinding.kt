package org.jire.netrune.net.server.netty4

import io.netty.channel.ChannelFuture
import com.mark.netrune.net.server.ServerBinding
import java.net.SocketAddress

class Netty4ServerBinding(
    override val localAddress: SocketAddress,

    override val bindFuture: ChannelFuture,
    override val channelCloseFuture: ChannelFuture
) : ServerBinding
