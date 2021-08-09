package jmfayard.dev.api

import jmfayard.dev.api.dto.Token
import jmfayard.dev.api.dto.Username
import jmfayard.dev.api.dto.UsernamePassword
import jmfayard.dev.api.dto.UsernameToken
import kotlinx.serialization.Serializable
import java.util.*

val UserDaoInstance: UserDao by lazy { InMemoryUserDao() }

interface UserDao {
    fun listRegisteredUsers(): List<Username>
    fun loginUser(usernamePassword: UsernamePassword): UsernameToken?
    fun userByToken(token: Token): Username?
    fun createUser(usernamePassword: UsernamePassword): UsernameToken?
}

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

fun UsernamePassword.invalidPassword(): Boolean =
    password.count() <= 7

@Serializable
data class GithubUser(

    val login: String,

    val id: Int,

    val node_id: String,

    val avatar_url: String,

    val gravatar_id: String,

    val url: String,

    val html_url: String,

    val followers_url: String,

    val following_url: String,

    val gists_url: String,

    val starred_url: String,

    val subscriptions_url: String,

    val organizations_url: String,

    val repos_url: String,

    val events_url: String,

    val received_events_url: String,

    val type: String,

    val site_admin: Boolean,

    val name: String,

    val company: String,

    val blog: String,

    val location: String,

    val email: String?,

    val hireable: Boolean,

    val bio: String,

    val twitter_username: String,

    val public_repos: Int,

    val public_gists: Int,

    val followers: Int,

    val following: Int,

    val created_at: String,

    val updated_at: String

)

fun GithubUser.toUserInformation(): UserInformation =
    UserInformation(
        id, login, avatar_url, UserInformationDetails(
            public_repos, public_gists, followers, following
        )
    )

@Serializable
data class UserInformation(
    val id: Int,
    val login: String,
    val avatar: String,
    val details: UserInformationDetails
)

@Serializable
data class UserInformationDetails(
    val public_repos: Int,
    val public_gists: Int,
    val followers: Int,
    val following: Int,
)