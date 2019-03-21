package com.bksoftware.security

import com.bksoftware.service_impl.AppUserServiceImpl
import com.bksoftware.service_impl.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@EnableWebSecurity
class SecurityConfig(val userDetailsServiceImpl: UserDetailsServiceImpl,
                     val appUserServiceImpl: AppUserServiceImpl) : WebSecurityConfigurerAdapter() {


    @Bean
    fun restAuthenticationEntryPoint() = RestAuthenticationEntryPoint()

    @Bean
    fun customAccessDeniedHandler() = CustomAccessDeniedHandler()

    @Bean
    fun corsConfigurationSource():CorsConfigurationSource{
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/api/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsServiceImpl)
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/api/**/public/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .addFilter(JWTAuthorizationFilter(authenticationManager(),appUserServiceImpl))
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

}