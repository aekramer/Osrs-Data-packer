package com.mark.tasks.impl

import com.displee.cache.CacheLibrary
import com.mark.tasks.CacheTask
import com.mark.util.MapFunctions
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
            getFiles(mapsDirectory,"gz","dat").filter { it.name.startsWith("l") }.forEach { mapFile ->
                if (mapFile.name.first().toString() == "l") {

                    val objectFile = File(mapFile.parent,mapFile.name.replaceFirstChar { "m" })

                    if (objectFile.exists()) {

                        val loc = mapFile.nameWithoutExtension.replace(
                            "m",""
                        ).replace("l","").split("_")

                        var tileData = Files.readAllBytes(mapFile.toPath())
                        var objData = Files.readAllBytes(objectFile.toPath())

                        if (mapFile.name.endsWith(".gz")) {
                            tileData = decompressGzipToBytes(mapFile.toPath())
                        }
                        if (objectFile.name.endsWith(".gz")) {
                            objData = decompressGzipToBytes(objectFile.toPath())
                        }

                        MapFunctions.packMap(library,loc[0].toInt(), loc[1].toInt(), tileData, objData)

                    } else {
                        println("MISSING MAP FILE: $objectFile")
                    }

                }

                progressMaps.step()
                progressMaps.step()
            }
            progressMaps.close()
        }
    }

}