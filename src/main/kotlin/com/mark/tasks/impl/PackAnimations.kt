package com.mark.tasks.impl

import com.displee.cache.CacheLibrary
import com.mark.ArchiveIndex
import com.mark.tasks.CacheTask
import com.mark.util.*
import java.io.File
import java.nio.file.Files

/*
 * Packs Animations into the osrs cache
 */
class PackAnimations(private val animationDirectory : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val modelSize = getFiles(animationDirectory,"gz","dat").size
        val progressModels = progress("Packing Animations", modelSize)
        if (modelSize != 0) {
            getFiles(animationDirectory,"gz","dat").forEach {
                val id = it.nameWithoutExtension.toInt()
                val buffer = if (it.extension == "gz") decompressGzipToBytes(it.toPath()) else Files.readAllBytes(it.toPath())

                library.put(ArchiveIndex.ANIMATIONS, id, buffer)

                progressModels.step()
            }
            library.index(ArchiveIndex.ANIMATIONS).update()
            progressModels.close()
        }
    }

}