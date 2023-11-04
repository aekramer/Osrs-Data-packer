package com.mark.tasks.impl

import com.displee.cache.CacheLibrary
import com.mark.ArchiveIndex
import com.mark.tasks.CacheTask
import com.mark.util.*
import java.io.File
import java.nio.file.Files

/*
 * Packs Skeletons into the osrs cache
 */
class PackSkeletons(private val skellyDirectory : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val modelSize = getFiles(skellyDirectory,"gz","dat").size
        val progressModels = progress("Packing Skeletons", modelSize)
        if (modelSize != 0) {
            getFiles(skellyDirectory,"gz","dat").forEach {
                val id = it.nameWithoutExtension.toInt()
                val buffer = if (it.extension == "gz") decompressGzipToBytes(it.toPath()) else Files.readAllBytes(it.toPath())

                library.put(ArchiveIndex.SKELETONS, id, buffer)

                progressModels.step()
            }
            library.index(ArchiveIndex.SKELETONS).update()
            progressModels.close()
        }
    }

}