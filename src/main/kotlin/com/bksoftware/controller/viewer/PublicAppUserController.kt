package com.bksoftware.controller.viewer

import com.bksoftware.entities.AppUser
import com.bksoftware.entities.form.LoginForm
import com.bksoftware.entities.form.RegisterForm
import com.bksoftware.security.JWTService
import com.bksoftware.service_impl.*
import com.bksoftware.validator.RegisterRegex
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/api/v1/public/app-user")
class PublicAppUserController(val appService: AppServiceImpl,
                              val appUserService: AppUserServiceImpl,
                              val jwtService: JWTService,
                              val appRoleService: AppRoleServiceImpl,
                              val sendMailService: SendMailServiceImpl,
                              val userVerifyService: UserVerifyServiceImpl) {

    @PostMapping("/register")
    fun createAppUser(@RequestBody registerForm: RegisterForm, request: HttpServletRequest): ResponseEntity<String> {
        when {
            !RegisterRegex.checkUsername(registerForm.username) -> return ResponseEntity("Invalid username", HttpStatus.BAD_REQUEST)
            !RegisterRegex.checkEmail(registerForm.email) -> return ResponseEntity("Invalid email", HttpStatus.BAD_REQUEST)
            !RegisterRegex.checkPhone(registerForm.phoneNumber) -> return ResponseEntity("Invalid phone number", HttpStatus.BAD_REQUEST)
        }
        val header = request.getHeader("AppName") ?: return ResponseEntity("AppName is null", HttpStatus.BAD_REQUEST)
        val app = appService.findAppByAppName(header)
                ?: return ResponseEntity("can not find App", HttpStatus.BAD_REQUEST)
        registerForm.username = "${registerForm.username} ${app.id}"
        val status = appUserService.checkRegister(registerForm, app)
        when (status) {
            1 -> return ResponseEntity("username was used", HttpStatus.BAD_REQUEST)
            2 -> return ResponseEntity("email was used", HttpStatus.BAD_REQUEST)
            3 -> return ResponseEntity("phone number was used", HttpStatus.BAD_REQUEST)
        }
        val appUser = appUserService.build(registerForm)
        appUser.app = app
        if (!appUserService.saveAppUser(appUser))
            return ResponseEntity("create fail", HttpStatus.BAD_REQUEST)
        val user:AppUser? = appUserService.findAppUserByUsername(appUser.username)
        if(!userVerifyService.createVerify(user!!))
            return ResponseEntity("create verify error",HttpStatus.BAD_REQUEST)
        val userVerify = userVerifyService.findUserVerifyByAppUser(user)
        val expiredTime= userVerify!!.expiredTime
        val time = "${expiredTime.hour}:${expiredTime.minute}:${expiredTime.second} ${expiredTime.dayOfMonth}-${expiredTime.month}-${expiredTime.year}"
        if(!sendMailService.sendMail(registerForm.email,
                "Verify your account",
                "Your verify code is ${userVerify.verifyCode}. Code will be expired at $time."))
            return ResponseEntity("send mail error",HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity("register success",HttpStatus.OK)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginForm: LoginForm, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse): ResponseEntity<String> {
        val header = httpServletRequest.getHeader("AppName")
                ?: return ResponseEntity("AppName is null", HttpStatus.BAD_REQUEST)
        val app = appService.findAppByAppName(header)
                ?: return ResponseEntity("can not find App", HttpStatus.BAD_REQUEST)
        loginForm.username += " ${app.id}"
        if (!appUserService.checkLogin(loginForm, app))
            return ResponseEntity("username or password is not correct", HttpStatus.UNAUTHORIZED)
        val appUser = appUserService.findAppUserByUsername(loginForm.username)
        if (!appUser!!.isVerified) return ResponseEntity("user was not verified",HttpStatus.FORBIDDEN)
        httpServletResponse.setHeader("token", jwtService.createToken(loginForm.username))
        return ResponseEntity("Login success", HttpStatus.OK)
    }

}