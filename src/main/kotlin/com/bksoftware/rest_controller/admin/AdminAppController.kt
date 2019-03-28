package com.bksoftware.rest_controller.admin

import com.bksoftware.entities.App
import com.bksoftware.service_impl.AppServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/v1/admin/app")
@RolesAllowed("ROLE_ADMIN")
class AdminAppController(val appServiceImpl: AppServiceImpl) {

    @PostMapping
    fun createApp(@RequestBody app: App): ResponseEntity<String> {
        app.isStatus = true
        return if (appServiceImpl.saveApp(app)) {
            ResponseEntity("create app success", HttpStatus.OK)
        } else ResponseEntity("create app fail", HttpStatus.BAD_REQUEST)
    }

    @PutMapping
    fun updateApp(@RequestBody app: App): ResponseEntity<String> {
        return if (appServiceImpl.saveApp(app)) {
            ResponseEntity("update app success", HttpStatus.OK)
        } else ResponseEntity("update app fail", HttpStatus.BAD_REQUEST)
    }

}