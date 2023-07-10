package com.mark.util

import com.displee.cache.CacheLibrary
import com.displee.cache.index.archive.Archive
import com.mark.ArchiveIndex
import java.util.*

object MapFunctions {

    fun packMap(library: CacheLibrary, regionX : Int, regionY : Int, tileData : ByteArray, objData : ByteArray) {
        val mapArchiveName = "m" + regionX + "_" + regionY
        val landArchiveName = "l" + regionX + "_" + regionY

        library.put(ArchiveIndex.MAPS.id, mapArchiveName, tileData)
        library.put(ArchiveIndex.MAPS.id, landArchiveName, objData)

    }

}