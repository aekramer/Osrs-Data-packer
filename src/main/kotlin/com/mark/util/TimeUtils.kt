package com.mark.util

import java.util.concurrent.TimeUnit

fun millsToFormat(mills : Long) : String = String.format("%d min, %d sec",
    TimeUnit.MILLISECONDS.toMinutes(mills),
    TimeUnit.MILLISECONDS.toSeconds(mills) -
    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mills))
)