package com.mark.netrune.endpoint.js5.incoming

import com.mark.netrune.endpoint.js5.Js5GroupRepository

@JvmInline
value class Js5OnDemandGroup(
    val bitpack: Int
) : Js5Group {

    constructor(archive: Int, group: Int) : this(Js5GroupRepository.bitpack(archive, group))

    override val archive get() = Js5GroupRepository.archive(bitpack)

    override val group get() = Js5GroupRepository.group(bitpack)

}