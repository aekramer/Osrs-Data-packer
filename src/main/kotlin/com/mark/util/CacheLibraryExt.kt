package com.mark.util

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.displee.cache.index.ReferenceTable
import com.displee.cache.index.archive.Archive
import com.mark.ArchiveIndex
import com.mark.ConfigType

fun CacheLibrary.getIndex(index : ArchiveIndex) : Index {
    return this.index(index.id)
}

fun ReferenceTable.getArchive(config : ConfigType) : Archive {
    return this.archive(config.id)!!
}
