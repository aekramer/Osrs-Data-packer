package com.mark.netrune.endpoint.game

import org.openrs2.crypto.SymmetricKey

interface XteaRepository {

    operator fun get(region: Int): SymmetricKey?

    operator fun set(region: Int, key: SymmetricKey)

}