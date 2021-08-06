# Go Technical Test

## Context

Your front-end team need's a new API on top of GitHub API to add custom functionnalities to the website they have been working on for 3 months.

As you once said, while have a coffee break with your workmates, that you did an API back in 1998, the project manager asked you to make this new API.

The team created some task for you that you need to do in order to help them.

You probably would've done that with Node.JS, but the client requires that this API **must** be done in Go.

"Time to learn something new" you thought and, as you've always been someone who loves to learn, you're happy to oblige.

## Requirements

- The project should be realised in Go
- The project should run with [golang 1.11.4](https://golang.org/doc/install)
- The project uses [glide](https://github.com/Masterminds/glide) for dependencies management
- You shouldn't need to add any library to the project.
- We will use [Gin Gonic](https://github.com/gin-gonic/gin) library to build the API.
- We will use [Go http module](https://golang.org/pkg/net/http/) to call GitHub API.
- You can add libraries for bonus points - make sure to use `glide` to install them.
- You shouldn't need a GitHub API key, all the GitHub API called in the subject are public.
- *All bonus points*, if you do bonus points, should be realised in the order given in the subject
- Bonus points will not be taken into account if the subject is not complete

## Getting started

- Install [golang 1.11.4](https://golang.org/doc/install)
- Install [glide](https://github.com/Masterminds/glide#install) `go get`
- We recommend using [Visual Studio Code](https://code.visualstudio.com/) with recommended Go extension to develop
- Clone this repository
- Use `glide install` to install project dependencies.
- Use `go run src/main.go` to run the project
- Open `http://127.0.0.1:8080/api/hello` url in your browser.
- You should see `{ 'hello': 'world!' }`
- Open the root folder of this project with your IDE and you are ready to work !

**Links**
- [Golang documentation](https://golang.org/doc/)
- [Gin documentation](https://gin-gonic.github.io/gin/) [[GitHub](https://github.com/gin-gonic/gin)]
- [GitHub API documentation](https://developer.github.com/v3/)

## How to read tasks

For each task, a small description will be given.

You should add endpoint - or webservices - to your API if you have the **WS** text in requirements for the task
```
WS: /api
^ This means you have to implement something that will print something when browsing http://127.0.0.1:8080/api
```

You should add a path parameter if the **WS** text contains a name between brackets `[]`
```
WS: /api/[username] ( )
^ This means it should work when browsing http://127.0.0.1:8080/api/lucas or http://127.0.0.1:8080/api/appstud
^ lucas or appstud in the exemple are the `[username]` in the URL.
```

You should add a query parameter to your API if you have the **Parameter** text in requirements for the task
```
Parameters:
    name = (description of the parameter)
^ This means it should work when browsing http://127.0.0.1:8080/api?name=lucas or http://127.0.0.1:8080/api?name=appstud
^ lucas or appstud in the exemple are the `name` parameter you need to implement.
```

Your WS should return the same content as specified in **Response** in requirements for the task
```
Response:
    {
        'name': 'hello',
        'time': (timestamp)
    }
^ This should be printed when browsing the WS associated with the task
^ We will not care about the newline, response can be with or without new line.
^ It should be a valid JSON, unless explicitly specified
^ Parenthesis in values is descripting the expected value. Here your WS will return the time in milliseconds instead of the (timestamp) word
```

## Fun time

### TASK-1001: Healthcheck endpoint

We need to endpoint to check if the API is alive.

**WS**: /api/healthcheck

**Response**:
```js
{
    "name": "github-api",
    "version": "1.0",
    "time": (timestamp)
}
```

### TASK-1002: Add a tiny easter egg

We usually add easter egg(s) in the end of a project ... but you couldn't resist ...

**WS**: /api/timemachine/logs/mcfly

**Response**:
```js
[
    {
        'name': 'My mom is in love with me',
        'version': '1.0',
        'time': -446723100
    },
    {
        'name': 'I go to the future and my mom end up with the wrong guy',
        'version': '2.0',
        'time': 1445470140
    },
    {
        'name': 'I go to the past and you will not believe what happens next',
        'version': '3.0',
        'time': -(Maximum int64 Value)
    }
]
```

### TASK-1003: Register user

We need an endpoint to register an user.

Normally, we would have use a POST HTTP method ... but for simplicity we will have it with GET.

We should probably store this somewhere in the code. It can be in memory for now.

**WS**: /api/users/register

**Parameters**:
- username = Login for the user account
- password = Password for the user account

**Response**:
```js
{
    'username': (username parameter should be here)
    'token': (a randomly created string)
}
```

### TASK-1004: List registered users

We need an endpoint to list users.

**WS**: /api/users

**Response**:
```js
[
    {
        'username': (username)
    },
    /* ... other users ... */
]
```

### TASK-1005: Login user

We need an endpoint to login a user.

This endpoint should return a **new** access token.

**WS**: /api/users/login

**Parameters**:
- username = Login for the user account
- password = Password for the user account

**Response**:
```js
{
    'username': (username),
    'token': (new randomly generated token)
}
```

### TASK-1006: Get connected user

We need an endpoint to retrieve the connected user.

**WS**: /api/users/me

**Parameters**:
- token = Token retrieved on login / register

**Response**:
```js
{
    'username': (username)
}
```

### TASK-1007: GitHub endpoint to retrieve feed

We want to have a GitHub activity feed. You can use�`/events` GitHub API endpoint.

**don't forget** to look at the (GitHub API documentation)[https://developer.github.com/v3/].

Request will fail with a 403 by default. Something is required by the GitHub API (and it's not a token).

**WS**: /api/github/feed

**Response**:
```js
[
    {
        "type": "PullRequestReviewCommentEvent",
        "actor": {
            "id": 6668460,
            "login": "jkotas"
        },
        "repo": {
            "id": 30092893,
            "name": "dotnet/coreclr"
        }
    },
    /* ... more events ... */
]
```

### TASK-1008: GitHub endpoint to retrieve a public user with login

We want to have an endpoint to retrieve GitHub user informations.

**WS**: /api/github/users/[actor_login]

**Response**:
```js
{
    'id': (id),
    'login': (login),
    'avatar': (avatar_url),
    'details': {
        'public_repos':	(user public repositories)
        'public_gists':	(user public gists)
        'followers': (user follower number)
        'following': (user following number)
    }
}
```

# Bonus points

### BONUS-1001: A correct exception handling system

We didn't handled exceptions in our program. It could be cool if the API had a real exception management strategy.

### BONUS-1002: Caching system for GitHub API

We could add a caching system on GitHub endpoints.

### BONUS-1003: Add a database system

Remember that our users are registered and stored in-memory ? In-memory means that if we reload the server, we don't have users registered anymore.

It might be great to plug a database, or use a database like SQLite which stores the data into a file.

### BONUS-1004: Make a frontend for the API - served by this same server

Choose whatever HTML / JS library and build a tiny web application to use the API.

The only requirement is that the application should be served by this program in Go.

You can go nuts on this one ;)
