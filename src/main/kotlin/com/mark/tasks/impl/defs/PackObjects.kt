package com.mark.tasks.impl.defs

import com.beust.klaxon.Klaxon
import com.displee.cache.CacheLibrary
import com.mark.ArchiveIndex
import com.mark.ConfigType
import com.mark.tasks.CacheTask
import com.mark.tasks.impl.defs.impl.ObjectDefinition
import com.mark.util.getArchive
import com.mark.util.getFiles
import com.mark.util.getIndex
import com.mark.util.progress
import java.io.File

/*
 * Packs Object Definitions into the cache
 */
class PackObjects(private val objectDir : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val objectDefsSize = getFiles(objectDir,"json").size
        val progressObjects = progress("Packing Objects", objectDefsSize)
        val errors : MutableMap<String, String> = emptyMap<String, String>().toMutableMap()
        if (objectDefsSize != 0) {
            getFiles(objectDir,"json").forEach {
                val def = Klaxon().parse<ObjectDefinition>(it.readText())?: return@forEach
                if (def.id == 0) {
                    errors[it.toString()] = "ID is 0 please set a id for the object to pack"
                    return@forEach
                }

                library.getIndex(ArchiveIndex.CONFIGS).getArchive(ConfigType.OBJECT).add(def.id, def.encode())

                progressObjects.step()
            }

            progressObjects.close()

            errors.forEach {
                println("[ERROR] ${it.key} : ${it.value}")
            }

        }
    }

}