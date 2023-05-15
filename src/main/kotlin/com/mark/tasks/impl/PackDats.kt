package com.mark.tasks.impl

import com.displee.cache.CacheLibrary
import com.mark.tasks.CacheTask
import com.mark.util.getFilesNoFilter
import com.mark.util.progress
import java.io.File

/*
 * Packs Dats into a custom Archive for our 317 users
 */
class PackDats(private val datsDirectory : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val datsSize = getFilesNoFilter(datsDirectory).size
        val progressdats = progress("Packing Data", datsSize)
        if (datsSize != 0) {
            getFilesNoFilter(datsDirectory).forEach {
                library.put(2, 40, it.nameWithoutExtension, it.readBytes())
                progressdats.step()
            }
            progressdats.close()
        }
    }

}