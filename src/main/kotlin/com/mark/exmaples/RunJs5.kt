package com.mark.exmaples

import com.mark.Application
import com.mark.Builder
import com.mark.TaskType
import java.io.File

/* Runs JS5 Server */

fun main() {
    val app = Builder(TaskType.RUN_JS5)
        .cacheLocation(File("./cache/"))
        .js5Revision(207)
    .build()
    app.initialize()
}