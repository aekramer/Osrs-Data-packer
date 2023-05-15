package com.mark.netrune.endpoint.login.outgoing

import com.mark.netrune.endpoint.OutgoingMessage

sealed interface LoginResponse : OutgoingMessage {

    data class SolveProofOfWork(
        val difficulty: Int,
        val text: String
    ) : LoginResponse

}