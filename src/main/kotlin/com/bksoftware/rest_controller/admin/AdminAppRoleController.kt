package com.bksoftware.rest_controller.admin

import com.bksoftware.entities.AppRole
import com.bksoftware.service_impl.AppRoleServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/v1/admin/app-role")
@RolesAllowed("ROLE_ADMIN")
@Secured("ROLE_ADMIN")
class AdminAppRoleController(val appRoleService: AppRoleServiceImpl) {

    @PostMapping
    fun createAppRole(@RequestBody appRole:AppRole):ResponseEntity<String>{
        appRole.isStatus = true
        return if (appRoleService.saveAppRole(appRole))
            ResponseEntity("saved", HttpStatus.OK)
        else ResponseEntity("saved fail", HttpStatus.BAD_REQUEST)
    }

    @PutMapping
    fun updateAppRole(@RequestBody appRole:AppRole):ResponseEntity<String>{
        return if (appRoleService.saveAppRole(appRole))
            ResponseEntity("updated", HttpStatus.OK)
        else ResponseEntity("update fail", HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/all")
    fun findAllRole() = ResponseEntity(appRoleService.findAll(),HttpStatus.OK)

}