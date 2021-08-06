package github

import (
	"appstud.com/github-core/src/common"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/monaco-io/request"
)

func FetchUser(c *gin.Context) {
	var actor_login = c.Param("actor_login")
	if actor_login == "" {
		c.JSON(400, common.NewError("github", errors.New("missing parameter actor_login")))
		return
	}
	client := request.Client{
		URL:    fmt.Sprintf("https://api.github.com/users/%s", actor_login),
		Method: "GET",
	}
	resp, err := client.Do()
	if err != nil {
		c.JSON(500, common.NewError("github", err))
		return
	}
	if resp.Code == 404 {
		c.JSON(404, common.NewError("github", errors.New(fmt.Sprintf("Username=%s does not exist on GitHub", actor_login))))
		return
	}
	var source GitHubUserInfoSource
	err = json.Unmarshal(resp.Data, &source)
	if err != nil {
		c.JSON(500, common.NewError("github", err))
		return
	}
	c.JSON(resp.Code, source.asGithubUserInfo())
}

type GithubUserInfo struct {
	Id      int32                  `json:"id"`
	Login   string                 `json:"login"`
	Avatar  string                 `json:"avatar"`
	Details *GithubUserInfoDetails `json:"details"`
}
type GithubUserInfoDetails struct {
	PublicRepos int32 `json:"public_repos"`
	PublicGists int32 `json:"public_gists"`
	Followers   int32 `json:"followers"`
	Following   int32 `json:"following"`
}

type GitHubUserInfoSource struct {
	Id          int32  `json:"id"`
	Login       string `json:"login"`
	AvatarUrl   string `json:"avatar_url"`
	PublicRepos int32  `json:"public_repos"`
	PublicGists int32  `json:"public_gists"`
	Followers   int32  `json:"followers"`
	Following   int32  `json:"following"`
}

func (s *GitHubUserInfoSource) asGithubUserInfo() GithubUserInfo {
	return GithubUserInfo{
		Id:     s.Id,
		Login:  s.Login,
		Avatar: s.AvatarUrl,
		Details: &GithubUserInfoDetails{
			PublicRepos: s.PublicRepos,
			PublicGists: s.PublicGists,
			Followers:   s.Followers,
			Following:   s.Following,
		},
	}
}
