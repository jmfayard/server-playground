package dev.jmfayard

import io.kotest.matchers.collections.containAll
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application

class UserRepositoryTests {

    private val dataApp = application {
        enable(dataConfig)
    }

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = dataApp.run(profiles = "test")
    }

    @Test
    fun count() {
        val repository = context.getBean<UserRepository>()
        runBlocking {
            assertEquals(3, repository.count())
        }
    }

    @Test
    fun findAll() {
        val repository = context.getBean<UserRepository>()
        val expected = listOf(
            User("smaldini", "Stéphane", "Maldini"),
            User("sdeleuze", "Sébastien", "Deleuze"),
            User("bclozel", "Brian", "Clozel")
        )
        runBlocking {
            repository.findAll().toList() should containAll(expected)
        }
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
