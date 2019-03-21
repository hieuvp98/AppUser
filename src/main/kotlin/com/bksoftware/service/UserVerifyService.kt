package com.bksoftware.service

import com.bksoftware.entities.AppUser
import com.bksoftware.entities.UserVerify

interface UserVerifyService {

    fun createVerify(appUser: AppUser):Boolean

    fun checkVerify(appUser: AppUser,code : String):Int

    fun generateCode():String

    fun findUserVerifyByAppUser(appUser: AppUser):UserVerify?
}