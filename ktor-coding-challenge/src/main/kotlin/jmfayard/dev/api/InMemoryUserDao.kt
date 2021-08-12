package jmfayard.dev.api

import jmfayard.dev.api.dto.Token
import jmfayard.dev.api.dto.Username
import jmfayard.dev.api.dto.UsernamePassword
import jmfayard.dev.api.dto.UsernameToken
import java.util.*

class InMemoryUserDao : UserDao {
    val users = mutableListOf<UsernamePassword>()
    val usersMap = mutableMapOf<String, String>()
    val sessions = mutableMapOf<String, Username>()

    fun createRandomToken(): String =
        UUID.randomUUID().toString()

    override fun listRegisteredUsers(): List<Username> {
        return users.map { Username(it.username) }
    }

    override fun loginUser(usernamePassword: UsernamePassword): UsernameToken? {
        val actualPassword = usersMap[usernamePassword.username] ?: return null
        if (actualPassword != usernamePassword.password) return null
        val token = createRandomToken()
        sessions[token] = Username(usernamePassword.username)
        return UsernameToken(usernamePassword.username, token)
    }

    override fun userByToken(token: Token): Username? {
        return sessions[token.token]
    }

    override fun createUser(usernamePassword: UsernamePassword): UsernameToken? {
        if (usernamePassword.invalidPassword()) return null
        if (usersMap[usernamePassword.username] != null) return null
        val token = createRandomToken()
        sessions[token] = Username(usernamePassword.username)
        usersMap[usernamePassword.username] = usernamePassword.password
        users.add(usernamePassword)
        return UsernameToken(usernamePassword.username, token)
    }
}