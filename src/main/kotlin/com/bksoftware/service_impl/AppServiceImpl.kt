package com.bksoftware.service_impl

import com.bksoftware.entities.App
import com.bksoftware.repository.AppRepository
import com.bksoftware.service.AppService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AppServiceImpl(val appRepository: AppRepository) : AppService {

    val logger: Logger = LoggerFactory.getLogger(AppUserServiceImpl::class.java)

    override fun findAppByAppName(appName: String): App? {
        return try {
            appRepository.findByAppName(appName)
        } catch (ex: Exception) {
            logger.error("find-app-error: {0}", ex.message)
            null
        }
    }

    override fun findAppById(id: String): App? {
        return try {
            appRepository.findById(id).get()
        } catch (ex: Exception) {
            logger.error("find-app-error: {0}", ex.message)
            null
        }
    }

    override fun saveApp(app: App): Boolean {
        return try {
            appRepository.save(app)
            true
        } catch (ex: Exception) {
            logger.error("save-app-error", ex.message)
            false
        }
    }
}