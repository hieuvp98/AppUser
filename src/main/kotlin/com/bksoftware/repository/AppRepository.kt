package com.bksoftware.repository

import com.bksoftware.entities.App
import org.springframework.data.mongodb.repository.MongoRepository

interface AppRepository : MongoRepository<App, String> {

    fun findByAppName(name: String): App

    fun findAppById(id: String): App

    override fun findAll(): List<App>

}