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
import org.springframework.web.bind.annotation.*

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
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))

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

    @DeleteMapping("/delete/{username}")
    fun delete(
        @PathVariable username: String?
    ): ResponseEntity<Usuario>?{

        if (username == null){
            throw BadRequestException("El nombre de usuario no puede estar en blanco.")
        }

        val user = userService.delete(username)

        return ResponseEntity(user, HttpStatus.OK)
    }

    @PutMapping("/update")
    fun update(
        @RequestBody userUpdate: Usuario?
    ): ResponseEntity<Usuario>?{
        if (userUpdate == null){
            throw BadRequestException("El usuario no puede estar vacío.")
        }

        val updateUser = userService.update(userUpdate)

        return ResponseEntity(updateUser, HttpStatus.OK)

    }

    @GetMapping("/{username}")
    fun getByName(
        @PathVariable username: String
    ): ResponseEntity<Usuario>{
        val usuario = userService.getByName(username)
        return ResponseEntity(usuario, HttpStatus.OK)
    }

}