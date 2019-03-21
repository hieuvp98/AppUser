package com.bksoftware.service

import com.bksoftware.entities.AppRole
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
interface AppRoleService {
    fun findAppRoleByRole(role:String):AppRole?

    fun findAppRoleById(id: String):AppRole?

    fun saveAppRole(appRole: AppRole):Boolean

    fun findAll():List<AppRole>
}