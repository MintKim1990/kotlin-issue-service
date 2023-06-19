package userservice.service

import org.springframework.stereotype.Service
import userservice.config.JwtProperties
import userservice.domain.entity.User
import userservice.domain.repository.UserRepository
import userservice.exception.PasswordNotMatchedException
import userservice.exception.UserExistException
import userservice.exception.UserNotFoundException
import userservice.model.SignInRequest
import userservice.model.SignInResponse
import userservice.model.SignUpRequest
import userservice.utils.BCryptUtils
import userservice.utils.JwtClaim
import userservice.utils.JwtUtils
import java.time.Duration

@Service
class UserService (
    private val userRepository: UserRepository,
    private val jwtProperties: JwtProperties,
    private val cacheManager: CoroutinCacheManager<User>
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

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

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()) {
            if (!BCryptUtils.verify(signInRequest.password, password)) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JwtClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )

            val token = JwtUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

}