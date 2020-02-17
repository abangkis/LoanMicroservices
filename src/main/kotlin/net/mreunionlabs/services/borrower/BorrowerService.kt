package net.mreunionlabs.services.borrower

import com.google.gson.FieldNamingPolicy
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.features.callIdMdc
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.Logger


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::borrowerModule).start(wait = true)
}


fun Application.borrowerModule() {
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
            throw cause
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

    install(CallLogging) {
        //        level = Level.TRACE
        callIdMdc("X-Request-ID")
    }

    routing {
        get("/health_check") {
            // Check databases/other services.
            call.respondText("OK")
        }

        get("/") {
            call.respondText("Hello, Borrower!", ContentType.Text.Html)
        }

        borrower(log)
    }
}

// Extracted route
fun Routing.borrower(log: Logger) {
    route("/borrower") {
        get("/health_check") {
            // Check databases/other services.
            call.respondText("OK")
        }

        get("/") {
            call.respond(Borrower(1, "Cibo"))
        }
    }

    get("/borrower-loan") {
        val service = RetrofitHelper.getService()

        val borrower = Borrower(1, "Cibo")
        val loan = service.getLoan()

        log.debug("loan $loan")

        val map = mapOf(
            "borrower" to borrower,
            "loan" to loan
        )
        call.respond(map)
    }
}


