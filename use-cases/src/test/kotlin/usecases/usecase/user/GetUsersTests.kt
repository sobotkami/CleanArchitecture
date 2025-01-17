package usecases.usecase.user

import domain.AuthorizationException
import domain.LoginException
import domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import usecases.model.UserModel
import usecases.usecase.UsecaseTests
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetUserTest : UsecaseTests {

    val repository = mockk<UserRepository>()
    override val usecase = GetUser(repository)

    @Test
    override fun success() {
        runBlocking {
            every { runBlocking { repository.findById(id) } } returns user
            val result = usecase(userModel, id)
            assertEquals(result, UserModel(user))
        }
    }

    @Test
    override fun unauthenticated() {
        runBlocking {
            assertFailsWith<LoginException> {
                usecase(null, id)
            }
        }
    }

    @Test
    override fun `No user roles`() {
        runBlocking {
            assertFailsWith<AuthorizationException> {
                usecase(userModel.copy(authorities = emptyList()), id)
            }
        }
    }
}
