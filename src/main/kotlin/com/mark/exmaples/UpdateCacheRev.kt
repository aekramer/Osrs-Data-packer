package com.mark.exmaples

import com.mark.Builder
import com.mark.TaskType
import com.mark.tasks.impl.RemoveXteas
import java.io.File

/* Allows you to update the cache to a certain rev fresh if [cacheRevision] not defined it will always use latest rev */

fun main() {
    val app = Builder(TaskType.UPDATE_REV)
        .cacheLocation(File("./cache/"))
        .extraTasks(RemoveXteas())
    .build()
    app.initialize()
}