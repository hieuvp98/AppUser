package com.bksoftware.service_impl

import com.bksoftware.entities.AppUser
import com.bksoftware.entities.UserVerify
import com.bksoftware.repository.UserVerifyRepository
import com.bksoftware.security.SecurityConstants
import com.bksoftware.service.UserVerifyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class UserVerifyServiceImpl(val userVerifyRepository: UserVerifyRepository) : UserVerifyService {

    val logger: Logger = LoggerFactory.getLogger(AppUserServiceImpl::class.java)


    override fun checkVerify(appUser: AppUser, code: String): Int {
        val userVerify = userVerifyRepository.findUserVerifyByAppUser(appUser)?: return -3
        when {
            userVerify.verifyCode != code -> return -2
            userVerify.expiredTime.isBefore(LocalDateTime.now()) -> return -1
        }
        return 1
    }

    override fun generateCode(): String {
        val text = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM"
        val random = Random()
        val code = StringBuilder()
        for (i in 0..9) {
            code.append(text[random.nextInt(text.length)])
        }
        return code.toString()
    }

    override fun createVerify(appUser: AppUser): Boolean {
        val userVerify = UserVerify()
        userVerify.appUser = appUser
        userVerify.verifyCode = generateCode()
        userVerify.expiredTime = Date(System.currentTimeMillis() + SecurityConstants.VERIFY_TIME)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        userVerify.isStatus = true
        return try {
            userVerifyRepository.save(userVerify)
            true
        } catch (ex: Exception) {
            logger.error("create verify error", ex.message)
            false
        }
    }

    override fun findUserVerifyByAppUser(appUser: AppUser): UserVerify? {
        return try {
            userVerifyRepository.findUserVerifyByAppUser(appUser)
        } catch (ex: Exception) {
            logger.error("find-user-verify-error", ex.message)
            return null
        }
    }
}