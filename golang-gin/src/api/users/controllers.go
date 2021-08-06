package users

import (
	"appstud.com/github-core/src/common"
	"github.com/gin-gonic/gin"
)

var store = initStore()

func ListUsers(c *gin.Context) {
	c.JSON(200, store.FetchAllUsers())
}

func Register(c *gin.Context) {
	var username = c.Query("username")
	var password = c.Query("password")

	var token, err = store.RegisterUser(username, password)

	if err != nil {
		c.JSON(401, common.NewError("register", err))
		return
	}
	c.JSON(200, UserTokenResponse{
		Username: username,
		Token:    token,
	})
}

func LoginUser(c *gin.Context) {
	var username = c.Query("username")
	var password = c.Query("password")

	var token, err = store.LoginUser(username, password)

	if err != nil {
		c.JSON(401, common.NewError("login", err))
		return
	}
	c.JSON(200, UserTokenResponse{
		Username: username,
		Token:    token,
	})
}

func GetConnectedUser(c *gin.Context) {
	var token = c.Query("token")
	var response, err = store.FetchByToken(token)
	if err != nil {
		c.JSON(401, common.NewError("authentification", err))
		return
	}
	c.JSON(200, response)
}
