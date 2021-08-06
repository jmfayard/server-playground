package api

import (
	"appstud.com/github-core/src/common"
	"github.com/gin-gonic/gin"
)

type HealthCheckResponse struct {
	Name    string `json:"name"`
	Version string `json:"version"`
	Time    int64  `json:"time"`
}

func IAmHealthy(c *gin.Context) {
	c.JSON(200, HealthCheckResponse{
		Name:    "github-api",
		Version: "1.0",
		Time:    common.Timestamp(),
	})
}
