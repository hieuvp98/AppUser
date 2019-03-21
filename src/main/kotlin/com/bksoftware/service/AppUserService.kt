package com.bksoftware.service

import com.bksoftware.entities.App
import com.bksoftware.entities.AppUser
import com.bksoftware.entities.form.LoginForm
import com.bksoftware.entities.form.RegisterForm
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface AppUserService {

    fun findById(id: String): AppUser?

    fun findAll(): List<AppUser>

    fun findAppUserByUsername(username: String): AppUser?

    fun saveAppUser(appUser: AppUser): Boolean

    fun findAppUserByUserNameAndApp(userName: String,app: App): AppUser?

    fun findAppUserByApp(app: App):List<AppUser>

    fun findAppUserByApp(app: App,pageable: Pageable):List<AppUser>

    fun checkRegister(appUser: RegisterForm,app: App):Int

    fun checkLogin(loginForm: LoginForm, app: App):Boolean

    fun build(registerForm: RegisterForm):AppUser

}