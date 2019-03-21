package com.bksoftware.service_impl

import com.bksoftware.entities.App
import com.bksoftware.entities.AppUser
import com.bksoftware.entities.form.LoginForm
import com.bksoftware.entities.form.RegisterForm
import com.bksoftware.repository.AppUserRepository
import com.bksoftware.service.AppUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AppUserServiceImpl(val appUserRepository: AppUserRepository,
                         val bCryptPasswordEncoder: BCryptPasswordEncoder,
                         val appRoleService: AppRoleServiceImpl) : AppUserService {

    val logger: Logger = LoggerFactory.getLogger(AppUserServiceImpl::class.java)

    override fun saveAppUser(appUser: AppUser): Boolean {
        return try {
            appUserRepository.save(appUser)
            true
        } catch (ex: Exception) {
            logger.error("save-app-user-error", ex.message)
            false
        }
    }

    override fun findById(id: String): AppUser? {
        return try {
            appUserRepository.findAppUserById(id)
        } catch (ex: Exception) {
            logger.error("find-by-id-error", ex.message)
            null
        }
    }

    override fun findAll(): List<AppUser> {
        return try {
            appUserRepository.findAll()
        } catch (ex: Exception) {
            logger.error("find-app-user-error", ex.message)
            emptyList()
        }
    }


    override fun findAppUserByUserNameAndApp(userName: String, app: App): AppUser? {
        return try {
            appUserRepository.findAppUserByUsernameAndApp(userName, app)
        } catch (ex: Exception) {
            logger.error("find-app-user-error", ex.message)
            null
        }
    }

    override fun findAppUserByApp(app: App): List<AppUser> {
        return try {
            appUserRepository.findAllByApp(app)
        } catch (ex: Exception) {
            logger.error("find-app-user-error", ex.message)
            listOf()
        }
    }

    override fun findAppUserByApp(app: App, pageable: Pageable): List<AppUser> {
        return try {
            appUserRepository.findAllByApp(app, pageable).content
        } catch (ex: Exception) {
            logger.error("find-app-user-error", ex.message)
            listOf()
        }
    }

    override fun checkRegister(appUser: RegisterForm, app: App): Int {
        val appUsers = appUserRepository.findAllByApp(app)
        appUsers.forEach { a ->
            run {
                if (appUser.username == a.username)
                    return 1 // username was used
                if (appUser.email == a.email)
                    return 2 // email was used
                if (appUser.phoneNumber.equals(a.phoneNumber))
                    return 3 // phone number was used
            }
        }
        return 0
    }

    override fun checkLogin(loginForm: LoginForm, app: App): Boolean {
     //   loginForm.password = bCryptPasswordEncoder.encode(loginForm.password)
        val appUsers = appUserRepository.findAllByApp(app)
        return appUsers.stream().filter { appUser -> appUser.username == loginForm.username &&
                        appUser.password == loginForm.password }.count() > 0
    }

    override fun findAppUserByUsername(username: String): AppUser? {
        return  try {
            appUserRepository.findAppUserByUsername(username)
        }catch (ex:Exception) {
            logger.error("find-app-user-error",ex.message)
            null
        }
    }

    override fun build(registerForm: RegisterForm): AppUser {
        val appUser = AppUser()
        appUser.username = registerForm.username
//        appUser.password = bCryptPasswordEncoder.encode(registerForm.password)
        appUser.email = registerForm.email
        appUser.phoneNumber = registerForm.phoneNumber.toInt()
        appUser.fullName = registerForm.fullName
        appUser.address = registerForm.address
        appUser.workPlace = registerForm.workPlace
        appUser.gender = registerForm.gender
        appUser.birthday = registerForm.birthDay
        appUser.isVerified = false
        appUser.isStatus = true
        appUser.appRoles = listOf(appRoleService.findAppRoleByRole("ROLE_USER"))
        return appUser
    }



}