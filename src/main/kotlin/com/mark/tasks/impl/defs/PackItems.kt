package com.mark.tasks.impl.defs

import com.beust.klaxon.Klaxon
import com.displee.cache.CacheLibrary
import com.mark.ArchiveIndex
import com.mark.ConfigType
import com.mark.tasks.CacheTask
import com.mark.tasks.impl.defs.impl.ItemDefinition
import com.mark.util.*
import java.io.File

/*
 * Packs Item Definitions into the cache
 */
class PackItems(private val npcDir : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val size = getFiles(npcDir,"json").size
        val progress = progress("Packing Items", size)
        val errors : MutableMap<String, String> = emptyMap<String, String>().toMutableMap()
        if (size != 0) {
            getFiles(npcDir,"json").forEach {
                val def = Klaxon().parse<ItemDefinition>(it.readText())?: return@forEach
                if (def.id == 0) {
                    errors[it.toString()] = "ID is 0 please set a id for the object to pack"
                    return@forEach
                }

                library.getIndex(ArchiveIndex.CONFIGS).getArchive(ConfigType.ITEM).add(def.id, def.encode())

                progress.step()
            }

            progress.close()

            errors.forEach {
                println("[ERROR] ${it.key} : ${it.value}")
            }

        }
    }

}