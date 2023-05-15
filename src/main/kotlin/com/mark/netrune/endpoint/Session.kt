package com.mark.netrune.endpoint

import com.mark.netrune.endpoint.login.incoming.LoginConnect
import org.openrs2.crypto.StreamCipher

interface Session {

    var service: Service

    var serverSeed: Long

    var decodeCipher: StreamCipher
    var encodeCipher: StreamCipher

    var proofDifficulty: Int
    var proofText: String

    var loginConnect: LoginConnect

}