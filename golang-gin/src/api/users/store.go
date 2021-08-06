package users

import (
	"errors"
	"math/rand"
)

// Store users and sessions - could be in memory or in a database
type Store interface {
	// Register a user and gives back a session
	RegisterUser(username string, password string) (string, error)

	LoginUser(username string, password string) (string, error)

	FetchByToken(token string) (UserResponse, error)

	FetchAllUsers() []UserResponse
}

func initStore() Store {
	var store = InMemoryStore{
		Users:    make(map[string]User),
		Sessions: make(map[string]User),
	}

	store.RegisterUser("admin", "password-password")
	return store
}

type InMemoryStore struct {
	Users    map[string]User
	Sessions map[string]User
}

func (s InMemoryStore) FetchByToken(token string) (UserResponse, error) {
	user, exists := s.Sessions[token]
	if !exists {
		return UserResponse{}, errors.New("user.invalidToken")
	}
	return UserResponse{
		Username: user.Username,
	}, nil

}

func (s InMemoryStore) RegisterUser(username string, password string) (string, error) {
	if username == "" {
		return "", errors.New("user.usernameMissing")
	}
	if password == "" {
		return "", errors.New("user.passwordMissing")
	}
	if len(password) <= 10 {
		return "", errors.New("user.passwordTooShort")
	}
	_, exists := s.Users[username]
	if exists {
		return "", errors.New("user.alreadyExists")
	}

	var token = generateRandomToken()
	var user = User{
		Username: username,
		Password: password,
	}
	s.Users[username] = user
	s.Sessions[token] = user
	return token, nil
}

func (s InMemoryStore) LoginUser(username string, password string) (string, error) {
	if username == "" {
		return "", errors.New("user.usernameMissing")
	}
	if password == "" {
		return "", errors.New("user.passwordMissing")
	}
	user, exists := s.Users[username]
	if !exists {
		return "", errors.New("user.userNotFound")
	}

	var token = generateRandomToken()
	s.Sessions[token] = user
	return token, nil
}

func (s InMemoryStore) FetchAllUsers() []UserResponse {
	var response []UserResponse
	for i := range s.Users {
		response = append(response, UserResponse{
			s.Users[i].Username,
		})
	}
	return response
}

var letters = []rune("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

func generateRandomToken() string {
	var n = 16
	b := make([]rune, n)
	for i := range b {
		b[i] = letters[rand.Intn(len(letters))]
	}
	return string(b)
}
