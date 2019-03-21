package com.bksoftware.service

import com.bksoftware.entities.App
import org.springframework.stereotype.Service

@Service
interface AppService {

    fun findAppByAppName(appName: String):App?

    fun findAppById(id:String):App?

    fun saveApp(app:App):Boolean


}