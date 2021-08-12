package jmfayard.dev.api

import jmfayard.dev.api.dto.Token
import jmfayard.dev.api.dto.Username
import jmfayard.dev.api.dto.UsernamePassword
import jmfayard.dev.api.dto.UsernameToken
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun main() {
    Database.connect("jdbc:h2:mem:users")
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Users, Sessions)
        User.new {
            username = "jmfayard"
            password = "1234"
        }
        println(User.all().toList())
    }
}

val UserDaoInstance: UserDao by lazy { SqlUserDao() }

interface UserDao {
    fun listRegisteredUsers(): List<Username>
    fun loginUser(usernamePassword: UsernamePassword): UsernameToken?
    fun userByToken(token: Token): Username?
    fun createUser(usernamePassword: UsernamePassword): UsernameToken?
}

class SqlUserDao() : UserDao {

    override fun listRegisteredUsers(): List<Username> =
        transaction {
            User.all().map { Username(it.username) }
        }

    fun createRandomToken(): String =
        UUID.randomUUID().toString()

    override fun loginUser(usernamePassword: UsernamePassword): UsernameToken? {
        try {
            val user = User.find { Users.username eq usernamePassword.username }.single()
            if (user.password == usernamePassword.password) {
                return transaction {
                    val session = Session.new {
                        username = usernamePassword.username
                        token = createRandomToken()
                    }
                    UsernameToken(session.username, session.token)
                }
            } else {
                return null
            }
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    override fun userByToken(token: Token): Username? = transaction {
        try {
            val user = Session.find { Sessions.token eq token.token }.single()
            Username(user.username)
        } catch (e: NoSuchElementException) {
            null
        }
    }

    override fun createUser(usernamePassword: UsernamePassword): UsernameToken? = transaction {
        if (usernamePassword.invalidPassword()) return@transaction null

        val iterable = User.find { Users.username eq usernamePassword.username }
        if (iterable.empty().not()) return@transaction null

        User.new {
            username = usernamePassword.username
            password = usernamePassword.password
        }

        val session = Session.new {
            username = usernamePassword.username
            token = createRandomToken()
        }
        UsernameToken(session.username, session.token)
    }
}

object Users : IntIdTable() {
    val username = varchar("username", 100).index()
    val password = varchar("password", 50)
}

object Sessions : IntIdTable() {
    val username = varchar("username", 100).index()
    val token = varchar("token", 50).index()
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var password by Users.password
}

class Session(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Session>(Sessions)

    var username by Sessions.username
    var token by Sessions.token
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