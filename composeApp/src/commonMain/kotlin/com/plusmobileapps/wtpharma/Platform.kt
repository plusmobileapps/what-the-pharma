package com.plusmobileapps.wtpharma

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform