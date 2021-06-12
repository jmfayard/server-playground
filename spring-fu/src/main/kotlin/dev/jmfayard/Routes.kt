package dev.jmfayard

import org.springframework.web.reactive.function.server.coRouter

fun routes(
    userHandler: UserHandler,
    httpBinHandler: HttpBinHandler
) = coRouter {
    GET("/", userHandler::listView)
    GET("/api/user", userHandler::listApi)
    GET("/api/user/{login}", userHandler::userApi)
    GET("/conf", userHandler::conf)
    GET("/get", httpBinHandler::get)
    POST("/post", httpBinHandler::post)
}