package com.bksoftware.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService {
    fun createToken(username:String):String{
        return SecurityConstants.TOKEN_PREFIX + JWT.create().withSubject(username)
                .withExpiresAt(Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
    }
}