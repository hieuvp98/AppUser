package com.bksoftware.rest_controller.admin

import com.bksoftware.entities.AppRole
import com.bksoftware.entities.AppUser
import com.bksoftware.service_impl.AppServiceImpl
import com.bksoftware.service_impl.AppUserServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed


@RestController
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/api/v1/admin/user")
class AdminAppUserController(val appUserService: AppUserServiceImpl,
                             val appService: AppServiceImpl) {


    @PutMapping("/set-roles")
    fun setRolesAppUser(@RequestParam("id") id: String, @RequestBody appRoles: List<AppRole>): ResponseEntity<String> {
        val appUser = appUserService.findById(id) ?: return ResponseEntity("can not find user", HttpStatus.BAD_REQUEST)
        appUser.appRoles = appRoles
        return if (appUserService.saveAppUser(appUser)) {
            ResponseEntity("updated", HttpStatus.OK)
        } else
            ResponseEntity("update fail", HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/app/{idApp}")
    fun findAllAppUserByApp(@PathVariable idApp: String): ResponseEntity<List<AppUser>> {
        val app = appService.findAppById(idApp) ?: return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
        return ResponseEntity(appUserService.findAppUserByApp(app), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findAppUserById(@PathVariable("id") id : String):ResponseEntity<AppUser>{
        val user = appUserService.findById(id) ?: return ResponseEntity(AppUser(),HttpStatus.BAD_REQUEST)
        return ResponseEntity(user,HttpStatus.OK)
    }
}