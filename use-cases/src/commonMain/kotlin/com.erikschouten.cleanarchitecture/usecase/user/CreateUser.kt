package com.erikschouten.cleanarchitecture.usecase.user

import com.erikschouten.cleanarchitecture.*
import com.erikschouten.cleanarchitecture.entity.Authorities
import com.erikschouten.cleanarchitecture.model.CreateUserModel
import com.erikschouten.cleanarchitecture.model.UserModel
import com.erikschouten.cleanarchitecture.repository.UserRepository
import com.erikschouten.cleanarchitecture.usecase.Mutation
import com.erikschouten.cleanarchitecture.usecase.UsecaseA1

@Mutation
class CreateUser(
    private val repository: UserRepository,
    private val userExists: UserExists,
    private val passwordEncoder: PasswordEncoder,
) : UsecaseA1<CreateUserModel, UserModel>(CreateUserModel::class, UserModel::class) {

    override val executor = { authentication: UserModel?, a0: CreateUserModel ->
        if (authentication == null) throw LoginException()
        if (!authentication.authorities.contains(Authorities.USER)) throw AuthorizationException()
        if (!email(a0.email)) throw EmailInvalidException()
        if (!password(a0.password)) throw PasswordInvalidException()
        if (userExists(authentication, a0.email)) throw EmailAlreadyExistsException()
        UserModel(repository.save(a0.toUser(passwordEncoder)))
    }
}