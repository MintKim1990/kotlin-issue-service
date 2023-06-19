package userservice.utils

import com.auth0.jwt.interfaces.DecodedJWT
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import userservice.config.JwtProperties

class JwtUtilsTest {

    private val logger = KotlinLogging.logger {}

    @Test
    fun createTokenTest() {
        val jwtClaim = JwtClaim(
            userId = 1,
            email = "test@gmail.com",
            profileUrl = "profile.jpg",
            username = "개발자"
        )

        val jwtProperties = JwtProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JwtUtils.createToken(claim = jwtClaim, properties = jwtProperties)
        assertNotNull(token)

        logger.info { "token : $token" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JwtClaim(
            userId = 1,
            email = "test@gmail.com",
            profileUrl = "profile.jpg",
            username = "개발자"
        )

        val jwtProperties = JwtProperties(
            issuer = "jara",
            subject = "auth",
            expiresTime = 3600,
            secret = "my-secret"
        )

        val token = JwtUtils.createToken(claim = jwtClaim, properties = jwtProperties)

        val decode: DecodedJWT = JwtUtils.decode(token, secret = jwtProperties.secret, issuer = jwtProperties.issuer)

        with(decode) {
            logger.info { "claims : $claims" }

            val userId = claims["userId"]!!.asLong()
            assertEquals(userId, jwtClaim.userId)

            val email = claims["email"]!!.asString()
            assertEquals(email, jwtClaim.email)

            val profileUrl = claims["profileUrl"]!!.asString()
            assertEquals(profileUrl, jwtClaim.profileUrl)

            val username = claims["username"]!!.asString()
            assertEquals(username, jwtClaim.username)
        }
    }

}