package api

import (
	"github.com/gin-gonic/gin"
)

func EasterEgg(c *gin.Context) {
	c.JSON(200, easterEggResponse())
}

func easterEggResponse() []HealthCheckResponse {
	var response = []HealthCheckResponse{
		{
			Name:    "My mom is in love with me",
			Version: "1.0",
			Time:    -446723100,
		},
		{
			Name:    "I go to the future and my mom end up with the wrong guy",
			Version: "2.0",
			Time:    1445470140,
		},
		{
			Name:    "I go to the past and you will not believe what happens next",
			Version: "3.0",
			Time:    9223372036854775807,
		},
	}
	return response
}
