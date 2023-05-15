package com.mark.netrune.endpoint.login.incoming

@JvmInline
value class LoginProofOfWork(
    val nonce: Long
) : LoginIncomingMessage