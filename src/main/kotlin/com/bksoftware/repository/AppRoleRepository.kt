package com.bksoftware.repository

import com.bksoftware.entities.AppRole
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface AppRoleRepository:MongoRepository<AppRole,ObjectId> {

    fun findAppRoleByRole(role:String):AppRole

    fun findAppRoleById(id:String):AppRole

    override fun findAll():List<AppRole>
}