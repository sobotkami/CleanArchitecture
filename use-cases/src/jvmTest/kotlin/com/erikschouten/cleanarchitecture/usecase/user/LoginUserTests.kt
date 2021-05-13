package com.erikschouten.cleanarchitecture.usecase.user

import com.erikschouten.cleanarchitecture.dependency.Authenticator
import com.erikschouten.cleanarchitecture.LoginException
import com.erikschouten.cleanarchitecture.dependency.PasswordEncoder
import com.erikschouten.cleanarchitecture.entity.Password
import com.erikschouten.cleanarchitecture.entity.PasswordHash
import com.erikschouten.cleanarchitecture.model.LoginUserModel
import com.erikschouten.cleanarchitecture.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoginUserTests {

    val repository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val authenticator = mockk<Authenticator>()
    val usecase = LoginUser(repository, authenticator, passwordEncoder)

    val loginUserModel = LoginUserModel(email.value, password.value)

    @Test
    fun `Successful login`() {
        every { repository.findByEmail(email) } returns user
        every { passwordEncoder.matches(password, PasswordHash(password.value.reversed())) } returns true
        every { authenticator.generate(user.id) } returns "token"
        val result = usecase(null, loginUserModel)
        assertEquals(result, "token")
    }

    @Test
    fun `Invalid email`() {
        assertFailsWith<LoginException> {
            every { repository.findByEmail(email) } returns null
            usecase(null, loginUserModel)
        }
    }

    @Test
    fun `Invalid password`() {
        assertFailsWith<LoginException> {
            val pass = password.value + 1
            every { repository.findByEmail(email) } returns user
            every { passwordEncoder.matches(Password(pass), PasswordHash(password.value.reversed())) } returns false
            usecase(null, loginUserModel.copy(password = pass))
        }
    }
}
