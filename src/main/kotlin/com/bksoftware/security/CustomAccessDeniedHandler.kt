package com.bksoftware.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler:AccessDeniedHandler {
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, ex: AccessDeniedException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN)
            response.writer.print("Access denied")
    }
}