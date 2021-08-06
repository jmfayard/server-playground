package users

type User struct {
	Username string `json:"username"`
	Password string
}

type UserTokenResponse struct {
	Username string `json:"username"`
	Token    string `json:"token"`
}

type UserResponse struct {
	Username string `json:"username"`
}
