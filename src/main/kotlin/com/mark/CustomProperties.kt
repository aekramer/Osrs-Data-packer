package com.mark
import java.io.File
import java.util.Properties

data class PropertiesConfig(var cacheLocation : String = "", var revision : String = "-1") {

    private val properties : Properties = Properties()


    fun load(configLocation : File) {
        properties.load(configLocation.inputStream())
        cacheLocation = properties.getProperty("cacheLocation","")
        revision = properties.getProperty("revision","-1")
    }

    fun getCacheBase() : File {
        Application.builder.cacheLocation?.let {
            return Application.builder.cacheLocation!!
        } ?: run {
            if(cacheLocation.isEmpty()) {
                error("cacheLocation is not present in config please input it or define inside the constrcutor")
            }
            return File(cacheLocation)
        }
    }

    fun getRevision() : Int {
        return Integer.parseInt(revision)
    }

}