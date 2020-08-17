package com.vexdev

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlin.test.*
import io.ktor.server.testing.*

class CheckoutEndpointTest {
    @Test
    fun emptyBodyReturns400() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/checkout") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("")
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }
}
