package com.erikschouten.cleanarchitecture.usecases.user

import com.erikschouten.cleanarchitecture.domain.entity.user.*
import com.erikschouten.cleanarchitecture.usecases.model.UserModel

val email = Email("erik@erikschouten.com")
val password = Password("P@ssw0rd!")
val passwordHash = PasswordHash(password.value.reversed())
val id = 1
val user = User(id = id, email = email, authorities = listOf(Authorities.USER), password = passwordHash)
val userModel = UserModel(user)
