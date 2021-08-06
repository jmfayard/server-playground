package github

import (
	"appstud.com/github-core/src/common"
	"github.com/gin-gonic/gin"
	"github.com/monaco-io/request"
)

func FetchFeed(c *gin.Context) {
	client := request.Client{
		URL:    "https://api.github.com/events",
		Method: "GET",
	}
	resp, err := client.Do()
	if err != nil {
		c.JSON(500, common.NewError("github", err))
		return
	}
	c.Data(resp.Code, "application/json", resp.Data)
}
