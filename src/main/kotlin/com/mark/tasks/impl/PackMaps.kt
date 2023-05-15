package com.mark.tasks.impl

import com.displee.cache.CacheLibrary
import com.mark.tasks.CacheTask
import com.mark.util.decompressGzipToBytes
import com.mark.util.getFiles
import com.mark.util.progress
import java.io.File
import java.nio.file.Files

/*
 * Packs Maps into the osrs cache
 */
class PackMaps(private val mapsDirectory : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val mapSize = getFiles(mapsDirectory,"gz","dat").size
        val progressMaps = progress("Packing Maps", mapSize)
        if (mapSize != 0) {
            getFiles(mapsDirectory,"gz","dat").forEach {
                val id = it.nameWithoutExtension.toInt()
                val buffer = if (it.extension == ".gz") decompressGzipToBytes(it.toPath()) else Files.readAllBytes(it.toPath())
                library.put(5, id, buffer!!)
                progressMaps.step()
            }
            progressMaps.close()
        }
    }

}