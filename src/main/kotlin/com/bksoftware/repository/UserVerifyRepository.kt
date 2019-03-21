package com.bksoftware.repository

import com.bksoftware.entities.AppUser
import com.bksoftware.entities.UserVerify
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserVerifyRepository : MongoRepository<UserVerify,String> {

    fun findUserVerifyByAppUser(appUser: AppUser):UserVerify?


}