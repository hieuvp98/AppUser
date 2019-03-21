package com.bksoftware.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.bksoftware.service_impl.AppUserServiceImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authenticationManager: AuthenticationManager, private val appUserService: AppUserServiceImpl) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(SecurityConstants.HEADER_STRING)
        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            val authenticationToken = getAuthentication(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
            chain.doFilter(request, response)
        } else
            chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.HEADER_STRING) ?: null
        val username = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray())).build()
                .verify(token!!.replace(SecurityConstants.TOKEN_PREFIX, "")).subject ?: return null
        val appUser = appUserService.findAppUserByUsername(username) ?: return null
        return UsernamePasswordAuthenticationToken(username, null, appUser.grantedAuthority)

    }

}