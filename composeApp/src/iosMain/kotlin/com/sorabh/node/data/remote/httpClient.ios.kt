package com.sorabh.node.data.remote

import com.sorabh.node.ui.utils.Constant
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun httpClient(): HttpClient  = HttpClient(Darwin) {

    defaultRequest {
        url(Constant.BASE_URL)
    }

    install(DefaultRequest) {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }

    install(HttpTimeout){
        requestTimeoutMillis = 10000
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        )
    }

    install(Logging){
        logger = object : Logger{
            override fun log(message: String) {
                println(message)
            }

        }
    }
}