package com.mark.tasks.impl

import com.displee.cache.CacheLibrary
import com.mark.ArchiveIndex
import com.mark.XteaLoader
import com.mark.tasks.CacheTask
import com.mark.util.progress
import com.mark.util.put

/*
 * Removes Xteas Encryption from the maps
 */
class RemoveXteas : CacheTask() {
    override fun init(library: CacheLibrary) {
        val index = library.index(5)
        var mapCount = 0
        for (x in 0..256) {
            for (y in 0..256) {
                val landscapeId = index.archiveId("l${x}_${y}")
                if (landscapeId != -1) {
                    mapCount++
                }
            }
        }
        val mapProgress = progress("Removing Xteas Maps", mapCount)

        for (x in 0..256) {
            for (y in 0..256) {
                val regionId = x shl 8 or y
                val landscapeId = index.archiveId("l${x}_${y}")
                if (landscapeId != -1) {
                    val keys = XteaLoader.getKeys(regionId)
                    val landscape = library.data(5, "l${x}_${y}", keys)
                    if (landscape != null) {
                        library.put(ArchiveIndex.MAPS, "l${x}_${y}", landscape)
                    }
                    mapProgress.step()
                }
            }
        }
        mapProgress.close()
    }
}