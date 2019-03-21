package com.bksoftware.service

import org.springframework.stereotype.Service

@Service
interface SendMailService {

    fun sendMail(address: String, header: String, content: String): Boolean

}