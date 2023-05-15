package com.mark.netrune.buffer

interface Buffer : AutoCloseable {

    fun has(bytes: Int): Boolean

}
