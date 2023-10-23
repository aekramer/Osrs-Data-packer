package com.mark

import com.displee.cache.CacheLibrary
import com.mark.util.FileUtil
import mu.KotlinLogging
import org.jire.js5server.Js5Service
import java.io.File

class Application(configs: Builder) {

    private val logger = KotlinLogging.logger {}

    init {
        builder = configs
    }

    companion object {
        var library : CacheLibrary? = null
        lateinit var builder : Builder
    }

    fun initialize() {

        println(builder.toString())

        if (builder.cacheLocation == DEFAULT_PATH) logger.info { "Using Default path of [${DEFAULT_PATH.absolutePath}]" }

        when(builder.type) {
            TaskType.RUN_JS5 -> initJs5()
            TaskType.BUILD -> {
                val tempPath = FileUtil.getTemp()
                builder.cacheLocation.listFiles()?.filter { it.extension.contains("dat") || it.extension.contains("idx") }
                    ?.forEach {
                        val loc = File(tempPath,it.name)
                        it.copyTo(loc,true)
                    }
                library = CacheLibrary(tempPath.toString())
                try {
                    Packer.init()
                }catch (e : Exception) {
                    tempPath.delete()
                    e.printStackTrace()
                    logger.error { "Unable to build cache" }
                }
            }
            TaskType.UPDATE_REV -> {
                DownloadOSRS.init()
            }
        }
    }

    private fun initJs5() {
        val service = Js5Service()

        for (port in builder.js5Ports) {
            service.listen(port)
        }


    }

}