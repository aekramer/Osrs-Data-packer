package com.mark.netrune.endpoint.js5.incoming

sealed interface Js5Group : Js5IncomingMessage {

    val archive: Int
    val group: Int

}