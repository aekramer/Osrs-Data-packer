package com.mark.netrune.endpoint.login.incoming

import io.netty.buffer.ByteBuf
import com.mark.netrune.endpoint.VariableShortMessageDecoder
import org.openrs2.crypto.StreamCipher

object LoginProofOfWorkDecoder : VariableShortMessageDecoder<LoginProofOfWork>() {

    override fun decode(input: ByteBuf, opcode: Int, cipher: StreamCipher): LoginProofOfWork {
        val nonce = input.readLong()
        return LoginProofOfWork(nonce)
    }

}