package usecases.usecase.user

import domain.entity.user.Authorities
import domain.entity.user.Email
import domain.repository.UserRepository
import usecases.model.UserModel
import usecases.usecase.Query
import usecases.usecase.UsecaseA1
import kotlin.reflect.typeOf

@Query
class UserExists(
    private val repository: UserRepository
) : UsecaseA1<Email, Boolean>(typeOf<Email>(), typeOf<Boolean>()) {

    override val authorities = listOf(Authorities.USER)
    override suspend fun executor(authentication: UserModel?, a0: Email): Boolean {
        return repository.findByEmail(a0) != null
    }
}
