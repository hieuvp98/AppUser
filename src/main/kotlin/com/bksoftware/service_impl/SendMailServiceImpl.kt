package com.bksoftware.service_impl

import com.bksoftware.service.SendMailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class SendMailServiceImpl(val javaMailSender: JavaMailSender): SendMailService {

    @Value("\${spring.mail.username}")
    lateinit var hostMail:String

    val logger: Logger = LoggerFactory.getLogger(AppUserServiceImpl::class.java)

    override fun sendMail(address: String, header: String, content: String): Boolean {
        return try {
            val mail = SimpleMailMessage()
            mail.setTo(address)
            mail.setFrom(hostMail)
            mail.setSubject(header)
            mail.setText(content)
            javaMailSender.send(mail)
            true
        }catch (ex:MailException){
            logger.error("send mail error",ex.message)
            false
        }
    }
}