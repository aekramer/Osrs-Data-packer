package com.mark

import com.displee.cache.CacheLibrary
import com.mark.js5.Endpoint
import com.mark.js5.InitService
import com.mark.util.FileUtil
import io.netty.channel.EventLoopGroup
import com.mark.netrune.endpoint.Service
import com.mark.netrune.endpoint.js5.Js5GroupRepository
import com.mark.netrune.endpoint.js5.Openrs2Js5GroupRepository
import com.mark.netrune.net.netty4.DefaultEventLoopGroupFactory
import com.mark.netrune.net.netty4.EventLoopGroupFactory
import org.openrs2.cache.DiskStore
import org.openrs2.cache.Js5MasterIndex
import org.openrs2.cache.Store
import java.io.File
class Application() {

    constructor(builder: Builder) : this() {
        Companion.builder = builder
    }

    companion object {
        var properties : PropertiesConfig = PropertiesConfig()
        var library : CacheLibrary? = null
        lateinit var builder : Builder
    }

    fun initialize() {
        loadSettings()
        when(builder.type) {
            TaskType.RUN_JS5 -> {
                library = CacheLibrary(properties.getCacheBase().toString())
                initJs5()
            }
            TaskType.BUILD -> {
                val tempPath = FileUtil.getTemp()
                properties.getCacheBase().listFiles().filter { it.extension.contains("dat") || it.extension.contains("idx") }.forEach {
                    val loc = File(tempPath,it.name)
                    it.copyTo(loc,true)
                }
                library = CacheLibrary(tempPath.toString())
                Packer.init()
            }
            TaskType.UPDATE_REV -> DownloadOSRS.init()
        }
    }

    private fun loadSettings() {
        if (!builder.settingLocation.exists()) {
            error("Unable to boot due to missing settingsLoc at [${builder.settingLocation}] please read the readMe if you need help")
        }
        properties.load(builder.settingLocation)

    }


    private fun initJs5() {
        val store: Store = DiskStore.open(properties.getCacheBase().toPath())
        val masterIndex: Js5MasterIndex = Js5MasterIndex.create(store)

        val js5GroupRepository: Js5GroupRepository = Openrs2Js5GroupRepository(store, masterIndex)
        js5GroupRepository.load()

        val service: Service = InitService(js5GroupRepository)

        val eventLoopGroupFactory: EventLoopGroupFactory = DefaultEventLoopGroupFactory
        val parentGroup: EventLoopGroup = eventLoopGroupFactory.eventLoopGroup(1)
        val childGroup: EventLoopGroup = eventLoopGroupFactory.eventLoopGroup()
        Endpoint(
            service,
            parentGroup, childGroup,
            intArrayOf(Endpoint.JS5_PORT_BASE),
        ).use { endpoint ->
            endpoint.run()
        }
    }

}