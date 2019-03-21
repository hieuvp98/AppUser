package com.bksoftware.controller.user

import com.bksoftware.entities.AppUser
import com.bksoftware.service_impl.AppUserServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/user/info")
@RolesAllowed("APP_USER")
class UserController(val appUserService: AppUserServiceImpl) {

    @GetMapping
    fun getInfo(httpServletRequest: HttpServletRequest):ResponseEntity<AppUser>{
        val username = httpServletRequest.userPrincipal.name
        return ResponseEntity(appUserService.findAppUserByUsername(username),HttpStatus.OK)
    }
}