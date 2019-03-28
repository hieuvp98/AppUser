package com.bksoftware.rest_controller.user

import com.bksoftware.entities.AppUser
import com.bksoftware.service_impl.AppUserServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/user")
@Secured("ROLE_USER", "ROLE_ADMIN", "ROLE_MOD")
class UserController(val appUserService: AppUserServiceImpl) {

    @GetMapping("/info")
    fun getInfo(httpServletRequest: HttpServletRequest): ResponseEntity<AppUser> {
        val username = httpServletRequest.userPrincipal.name
        return ResponseEntity(appUserService.findAppUserByUsername(username), HttpStatus.OK)
    }

    @PutMapping("/change-password")
    fun changePassword(@RequestParam("old") old: String,
                       @RequestParam("new") new: String,
                       request: HttpServletRequest): ResponseEntity<String> {
        val user = appUserService.findAppUserByUsername(request.userPrincipal.name)
                ?: return ResponseEntity("error", HttpStatus.INTERNAL_SERVER_ERROR)
        if (user.password != old) return ResponseEntity("old password is not correct", HttpStatus.BAD_REQUEST)
        user.password = new
        return if (appUserService.saveAppUser(user))
            ResponseEntity("change password success", HttpStatus.OK)
        else ResponseEntity("change password fail", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PutMapping("/info")
    fun changeInfo(request: HttpServletRequest, @RequestBody userNew: AppUser): ResponseEntity<String> {
        val user = appUserService.findAppUserByUsername(request.userPrincipal.name)
                ?: return ResponseEntity("error", HttpStatus.INTERNAL_SERVER_ERROR)
        when (appUserService.changeInfo(user, userNew)) {
            -2 -> return ResponseEntity("email was used", HttpStatus.BAD_REQUEST)
            -1 -> return ResponseEntity("phone number was used", HttpStatus.BAD_REQUEST)
        }
        return if (appUserService.saveAppUser(user))
            ResponseEntity("changed", HttpStatus.OK)
        else ResponseEntity("change error", HttpStatus.BAD_REQUEST)
    }
}