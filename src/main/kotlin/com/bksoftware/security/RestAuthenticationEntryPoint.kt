package com.bksoftware.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(p0: HttpServletRequest, response: HttpServletResponse, p2: AuthenticationException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        response.writer.print("Unauthenticated")
    }
}