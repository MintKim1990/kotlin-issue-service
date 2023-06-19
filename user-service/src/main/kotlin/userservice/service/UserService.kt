package userservice.service

import org.springframework.stereotype.Service
import userservice.domain.entity.User
import userservice.domain.repository.UserRepository
import userservice.exception.UserExistException
import userservice.model.SignUpRequest
import userservice.utils.BCryptUtils

@Service
class UserService (
    private val userRepository: UserRepository
) {

    suspend fun signUp(signUpRequest: SignUpRequest) {

        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username
            )

            userRepository.save(user)
        }

    }

}