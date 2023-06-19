package userservice.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import userservice.config.JwtProperties
import java.util.*

object JwtUtils {

    fun createToken(claim: JwtClaim, properties: JwtProperties) =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("email", claim.email)
            .withClaim("profileUrl", claim.profileUrl)
            .withClaim("username", claim.username)
            .sign(Algorithm.HMAC256(properties.secret))

    fun decode(token: String, secret: String, issuer: String): DecodedJWT {

        val jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()

        return jwtVerifier.verify(token)
    }

}

data class JwtClaim(
    val userId: Long,
    val email: String,
    val profileUrl: String,
    val username: String
)