package com.example.planificador.controller

import com.example.planificador.error.exception.BadRequestException
import com.example.planificador.model.Usuario
import com.example.planificador.service.TokenService
import com.example.planificador.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/usuarios")
class UserController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/login")
    fun login(
        @RequestBody user : Usuario
    ): ResponseEntity<Any>?
    {
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))
        }catch (e : AuthenticationException){
            return ResponseEntity(mapOf("mensaje" to " Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }

        var token = ""
        token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody newUser : Usuario?
    ): ResponseEntity<Usuario>?{
        if (newUser == null){
            throw BadRequestException("Debe introducir un nombre, contraseña y rol")
        }

        userService.registrar(newUser)

        return ResponseEntity(newUser, HttpStatus.CREATED)
    }
}