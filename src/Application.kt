package com.vexdev

import com.vexdev.model.api.CheckoutRequest
import com.vexdev.model.api.CheckoutResponse
import com.vexdev.repository.MockCatalogueRepository
import com.vexdev.service.CheckoutService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.request.ContentTransformationException

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val catalogueRepository = MockCatalogueRepository()
val checkoutService = CheckoutService(catalogueRepository)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    install(StatusPages) {
        exception<ContentTransformationException> { _ ->
            call.respond(HttpStatusCode.BadRequest)
        }
        exception<CheckoutService.MissingProductException> { _ ->
            call.respond(HttpStatusCode.NotFound)
        }
        exception<Throwable> { _ ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    routing {
        post("/checkout") {
            val request = call.receive<CheckoutRequest>()
            if (request.skus.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                val total = checkoutService.checkout(request.skus)
                call.respond(CheckoutResponse(total))
            }
        }
    }
}

