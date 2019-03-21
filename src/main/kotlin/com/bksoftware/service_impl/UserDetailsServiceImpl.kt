package com.bksoftware.service_impl

import com.bksoftware.repository.AppUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val appUserRepository: AppUserRepository) : UserDetailsService {

    override fun loadUserByUsername(un: String): UserDetails {
        val appUser = appUserRepository.findAppUserByUsername(un) ?: throw UsernameNotFoundException(un)
        return User(appUser.username,appUser.password,appUser.grantedAuthority)
    }
}