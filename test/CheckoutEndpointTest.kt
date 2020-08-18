package com.vexdev

import com.google.gson.Gson
import com.vexdev.model.api.CheckoutRequest
import com.vexdev.model.api.CheckoutResponse
import com.vexdev.repository.MockCatalogueRepository
import com.vexdev.repository.MockCatalogueRepository.Companion.CASIO_SKU
import com.vexdev.repository.MockCatalogueRepository.Companion.ROLEX_SKU
import com.vexdev.repository.MockCatalogueRepository.Companion.SWATCH_SKU
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlin.test.*
import io.ktor.server.testing.*
import java.math.BigDecimal

class CheckoutEndpointTest {

    private val gson: Gson = Gson()

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

    @Test
    fun noSkusReturns400() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/checkout") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(gson.toJson(CheckoutRequest(emptyList())))
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun sampleWithoutDiscounts() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/checkout") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(gson.toJson(CheckoutRequest(listOf(SWATCH_SKU, CASIO_SKU))))
            }.apply {
                val checkoutResponse = gson.fromJson(response.content, CheckoutResponse::class.java)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(CheckoutResponse(BigDecimal(80)), checkoutResponse)
            }
        }
    }

    @Test
    fun sampleWithDiscounts() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/checkout") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(gson.toJson(CheckoutRequest(listOf(ROLEX_SKU, ROLEX_SKU, ROLEX_SKU, ROLEX_SKU))))
            }.apply {
                val checkoutResponse = gson.fromJson(response.content, CheckoutResponse::class.java)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(CheckoutResponse(BigDecimal(300)), checkoutResponse)
            }
        }
    }

    @Test
    fun singleItem() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/checkout") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(gson.toJson(CheckoutRequest(listOf(ROLEX_SKU))))
            }.apply {
                val checkoutResponse = gson.fromJson(response.content, CheckoutResponse::class.java)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(CheckoutResponse(BigDecimal(100)), checkoutResponse)
            }
        }
    }

}
