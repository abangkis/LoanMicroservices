package net.mreunionlabs.services

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

// application basic see https://ktor.io/servers/application.html

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::mainModule).start(wait = true)
}

//fun main(args: Array<String>) {
//    val env = applicationEngineEnvironment {
//        module {
//            mymodule()
//        }
//        // Private API
//        connector {
//            host = "127.0.0.1"
//            port = 9090
//        }
//        // Public API
//        connector {
//            host = "0.0.0.0"
//            port = 8080
//        }
//    }
//    embeddedServer(Netty, env).start(true)
//}

fun Application.mainModule() {
    routing {
        get("/") {
            call.respondText("Hello, world!", ContentType.Text.Html)
        }
    }
}
