package com.mark.tasks.impl.objects

import com.beust.klaxon.Klaxon
import com.displee.cache.CacheLibrary
import com.mark.ArchiveIndex
import com.mark.ConfigType
import com.mark.tasks.CacheTask
import com.mark.util.getArchive
import com.mark.util.getFiles
import com.mark.util.getIndex
import com.mark.util.progress
import java.io.File

/*
 * Packs Npc Definitions into the cache
 */
class PackNpcs(private val npcDir : File) : CacheTask() {
    override fun init(library: CacheLibrary) {
        val npcDefsSize = getFiles(npcDir,"json").size
        val progressNpcs = progress("Packing Npcs", npcDefsSize)
        val errors : MutableMap<String, String> = emptyMap<String, String>().toMutableMap()
        if (npcDefsSize != 0) {
            getFiles(npcDir,"json").forEach {
                val def = Klaxon().parse<NpcDefinition>(it.readText())?: return@forEach
                if (def.id == 0) {
                    errors[it.toString()] = "ID is 0 please set a id for the object to pack"
                    return@forEach
                }

                library.getIndex(ArchiveIndex.CONFIGS).getArchive(ConfigType.NPC).add(def.id, def.encode())

                progressNpcs.step()
            }

            progressNpcs.close()

            errors.forEach {
                println("[ERROR] ${it.key} : ${it.value}")
            }

        }
    }

}