package com.bksoftware.repository

import com.bksoftware.entities.App
import com.bksoftware.entities.AppUser
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : MongoRepository<AppUser, ObjectId> {

    fun findAppUserById(id: String): AppUser

    override fun findAll(): List<AppUser>

    fun findAppUserByUsername(username: String): AppUser?

    fun findAppUserByUsernameAndApp(username: String,app: App):AppUser

    fun findAppUserByEmailAndApp(email:String,app:App) :AppUser

    fun findAppUserByPhoneNumberAndApp(phoneNumber:Int,app: App): AppUser

    fun findAllByApp(app : App):List<AppUser>

    fun findAllByApp(app: App, pageable: Pageable):Page<AppUser>

}