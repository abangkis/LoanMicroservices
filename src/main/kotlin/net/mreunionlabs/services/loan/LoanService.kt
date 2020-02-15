package net.mreunionlabs.services.loan

import com.google.gson.FieldNamingPolicy
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8090, module = Application::loanModule).start(wait = true)
}

fun Application.loanModule() {
    install(StatusPages){
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation) {
        //        jackson {
//            enable(SerializationFeature.INDENT_OUTPUT)
//            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
//        }

        // see https://ktor.io/servers/features/content-negotiation/gson.html
        gson {
            setPrettyPrinting()
            setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
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

        loan()
    }
}

fun Routing.loan() {
    route("/loan") {
        get("/health_check") {
            // Check databases/other services.
            call.respondText("OK")
        }

        get("/") {
            val loan = Loan(1, 100_000, 1)
            call.respond(loan)
        }
    }

}
