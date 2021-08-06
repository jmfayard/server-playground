package main

import (
	"appstud.com/github-core/src/api"
	"appstud.com/github-core/src/api/github"
	"appstud.com/github-core/src/api/users"
	"github.com/gin-gonic/gin"
)

func launchServer() {
	r := gin.Default()
	configureRoutes(r)
	r.Run() // listen and serve on 0.0.0.0:8080
}

func configureRoutes(engine *gin.Engine) {
	engine.GET("/api/hello", api.SayHello)
	engine.GET("/api/healthcheck", api.IAmHealthy)
	engine.GET("/api/timemachine/logs/mcfly", api.EasterEgg)

	u := engine.Group("/api/users")
	{
		u.GET("/", users.ListUsers)
		u.GET("/register", users.Register)
		u.GET("/login", users.LoginUser)
		u.GET("/me", users.GetConnectedUser)
	}

	gh := engine.Group("/api/github")
	{
		gh.GET("/feed", github.FetchFeed)
		gh.GET("/users/:actor_login", github.FetchUser)
	}
}
