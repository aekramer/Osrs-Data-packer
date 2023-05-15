package com.mark

import com.mark.tasks.CacheTask
import java.io.File

class Builder {

    var type: TaskType = TaskType.RUN_JS5
    var extraTasks: List<CacheTask> = emptyList()
    var settingLocation: File = File("./packerConfig.properties")
    var cacheLocation: File? = null

    fun taskType(type: TaskType) = apply { this.type = type }
    fun extraTasks(type: List<CacheTask>) = apply { this.extraTasks = type }

    fun configLocation(settingsLoc: File) = apply { this.settingLocation = settingsLoc }

    fun cacheLocation(cacheLocation: File) = apply { this.cacheLocation = cacheLocation }

    fun build() = Application(this)



}