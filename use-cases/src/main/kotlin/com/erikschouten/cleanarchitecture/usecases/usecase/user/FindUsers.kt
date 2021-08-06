package com.erikschouten.cleanarchitecture.usecases.usecase.user

import com.erikschouten.cleanarchitecture.domain.entity.user.Authorities
import com.erikschouten.cleanarchitecture.domain.entity.user.Email
import com.erikschouten.cleanarchitecture.domain.repository.UserRepository
import com.erikschouten.cleanarchitecture.usecases.model.UserModel
import com.erikschouten.cleanarchitecture.usecases.usecase.Query
import com.erikschouten.cleanarchitecture.usecases.usecase.UsecaseA1
import kotlin.reflect.typeOf

@Query
class FindUsers(
    private val repository: UserRepository,
) : UsecaseA1<List<Email>, List<UserModel>>(typeOf<List<Email>>(), typeOf<List<UserModel>>()) {

    override val authorities = listOf(Authorities.USER)
    override val executor: suspend (UserModel?, List<Email>) -> List<UserModel> = { _, emails ->
        repository.findAllByEmails(emails).map { UserModel(it) }
    }
}