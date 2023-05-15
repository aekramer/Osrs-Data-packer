package com.mark

import com.mark.Application.Companion.library
import com.mark.util.FileUtil
import java.io.File


object Packer {

    fun init() {

        library?.let {

            Application.builder.extraTasks.forEach { task ->
                task.init(it)
            }

            it.update()
            it.rebuild(FileUtil.getTempDir("rebuilt"))
            it.close()

            val tempPath = Application.properties.getCacheBase()
            FileUtil.getTempDir("rebuilt").listFiles().filter { it.extension.contains("dat") || it.extension.contains("idx") }.forEach {
                val loc = File(tempPath,it.name)
                it.copyTo(loc,true)
            }

            FileUtil.getTemp().deleteRecursively()
        } ?: run {
            error("Unable to load the cache")
        }

    }

}