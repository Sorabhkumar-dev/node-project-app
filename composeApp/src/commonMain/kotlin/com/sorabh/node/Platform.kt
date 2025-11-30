package com.sorabh.node

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform