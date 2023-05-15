package com.mark.netrune.endpoint

import com.mark.netrune.endpoint.login.incoming.LoginConnect
import org.openrs2.crypto.NopStreamCipher
import org.openrs2.crypto.StreamCipher

class DefaultSession(
    override var service: Service,

    override var decodeCipher: StreamCipher = NopStreamCipher,
    override var encodeCipher: StreamCipher = NopStreamCipher,

    override var serverSeed: Long = -1L,

    override var proofDifficulty: Int = 16
) : Session {

    override lateinit var proofText: String

    override lateinit var loginConnect: LoginConnect

}