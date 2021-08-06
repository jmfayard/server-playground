package api

import "github.com/gin-gonic/gin"

// HelloWorldResponse basic /api response
type HelloWorldResponse struct {
	Hello string `json:"hello"`
}

func SayHello(c *gin.Context) {
	c.JSON(200, HelloWorldResponse{
		Hello: "world!",
	})
}
