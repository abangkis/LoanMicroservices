package net.mreunionlabs.services.borrower

import io.ktor.application.*
import io.ktor.features.StatusPages
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::borrowerModule).start(wait = true)
}



fun Application.borrowerModule() {
    install(StatusPages){
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    routing {
        get("/health_check") {
            // Check databases/other services.
            call.respondText("OK")
        }

        get("/") {
            call.respondText("Hello, loan!", ContentType.Text.Html)
        }

        borrower()
    }
}

// Extracted route
fun Routing.borrower() {
    route("/borrower") {
        get("/health_check") {
            // Check databases/other services.
            call.respondText("OK")
        }

        get("/") {
            call.respondText("Get borrower!", ContentType.Text.Html)
        }
    }

}
