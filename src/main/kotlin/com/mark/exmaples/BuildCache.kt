package com.mark.exmaples

import com.mark.Builder
import com.mark.TaskType
import com.mark.tasks.impl.RemoveXteas
import java.io.File

/* Allows you to edit the cache like pack sprites [Objects,Npcs and Maps] */

fun main() {
    val app = Builder(TaskType.BUILD)
        .cacheLocation(File("./cache/"))
        .extraTasks(RemoveXteas())
    .build()
    app.initialize()
}