package com.mark.util

import com.mark.Application
import java.io.File

object FileUtil {

    fun getTemp() : File {
        val file = File(Application.properties.getCacheBase(),"/temp/")
        if(!file.exists()) file.mkdirs()
        return file
    }

    fun getTempDir(dir : String) : File {
        val file = File(Application.properties.getCacheBase(),"/temp/${dir}/")
        if(!file.exists()) file.mkdirs()
        return file
    }

}