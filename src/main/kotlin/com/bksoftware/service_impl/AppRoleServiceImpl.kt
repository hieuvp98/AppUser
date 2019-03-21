package com.bksoftware.service_impl

import com.bksoftware.entities.AppRole
import com.bksoftware.repository.AppRoleRepository
import com.bksoftware.service.AppRoleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AppRoleServiceImpl(val appRoleRepository: AppRoleRepository) : AppRoleService {

    val logger: Logger = LoggerFactory.getLogger(AppUserServiceImpl::class.java)

    override fun saveAppRole(appRole: AppRole): Boolean {
        return try {
            appRoleRepository.save(appRole)
            true
        } catch (ex: Exception) {
            logger.error("save-app-role-error", ex.message)
            false
        }
    }

    override fun findAppRoleByRole(role: String): AppRole? {
        return try {
            appRoleRepository.findAppRoleByRole(role)
        } catch (ex: Exception) {
            logger.error("find-app-role-error", ex.message)
            null
        }
    }

    override fun findAppRoleById(id: String): AppRole? {
        return try {
            appRoleRepository.findAppRoleById(id)
        } catch (ex: Exception) {
            logger.error("find-app-role-error", ex.message)
            null
        }

    }

    override fun findAll(): List<AppRole> {
        return try {
            appRoleRepository.findAll()
        } catch (ex: Exception) {
            logger.error("find-all-app-role-error", ex.message)
            listOf()
        }
    }
}