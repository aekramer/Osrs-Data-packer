package com.mark

import com.google.gson.Gson
import com.mark.util.FileUtil
import mu.KotlinLogging
import java.io.File

val logger = KotlinLogging.logger {}

data class Xtea(
    val mapsquare: Int,
    val key: IntArray
)

object XteaLoader {

    var xteas: MutableMap<Int, Xtea> = emptyMap<Int, Xtea>().toMutableMap()
    val xteasList: MutableMap<Int, IntArray> = HashMap<Int, IntArray>().toMutableMap()

    fun load() {
        val file = File(FileUtil.getTemp(), "xteas.json")
        val data = Gson().fromJson(file.readText(), Array<Xtea>::class.java)
        data.forEach {
            xteas[it.mapsquare] = it
            xteasList[it.mapsquare] = it.key
        }
        logger.info { "Keys Loaded: ${xteasList.size}" }

    }

    fun getKeys(region: Int): IntArray? {
        if (xteasList.containsKey(region)) {
            return xteasList[region]!!
        }
        return null
    }

}