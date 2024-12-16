package com.example.planificador.service

import com.example.planificador.error.exception.BadRequestException
import com.example.planificador.error.exception.UserExistException
import com.example.planificador.error.exception.UserNotFoundException
import com.example.planificador.model.Usuario
import com.example.planificador.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService: UserDetailsService {

    @Autowired
    private lateinit var userRespository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username:String?): UserDetails {

        val usuario = userRespository
            .findByUsername(username!!)
            .orElseThrow{BadRequestException("Credenciales erróneas")}

        val roles: List<SimpleGrantedAuthority> = usuario.roles
            ?.split(",")
            ?.map { rol -> SimpleGrantedAuthority("ROLE_" + rol) }
            ?.toList() ?: listOf()

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .authorities(roles)
            .build()
    }

    fun registrar(newUsuario: Usuario): Usuario? {

        val user = userRespository
            .findByUsername(newUsuario.username)

        if (user.isPresent) {
            throw UserExistException("Usuario existente")
        }

        comprobarUser(newUsuario)

        newUsuario.password = passwordEncoder.encode(newUsuario.password)
        userRespository.save(newUsuario)

        return newUsuario

    }

    fun delete(username: String): Usuario {
        val usuario = userRespository.findByUsername(username).orElseThrow{UserNotFoundException("No se encontró al usuario con nombre: $username")}
        userRespository.delete(usuario)
        return usuario
    }

    fun update(updateUser: Usuario): Usuario{
        comprobarUser(updateUser)
        val usuario = userRespository.findByUsername(updateUser.username).orElseThrow{UserNotFoundException("No se encontró al usuario con nombre: ${updateUser.username}")}
        if (usuario != null) {
            usuario.username = updateUser.username
            usuario.password = passwordEncoder.encode(updateUser.password)
            if (updateUser.roles?.uppercase() != "ADMIN" && updateUser.roles?.uppercase() != "USER") {
                throw BadRequestException("El campo rol es obligatorio")
            }
            usuario.roles = updateUser.roles
            return userRespository.save(usuario)
        }
        throw UserNotFoundException("No se encontro ningún usuario con id: ${updateUser.id}")
    }

    fun getByName(username: String): Usuario {
        val usuario = userRespository.findByUsername(username).orElseThrow{UserNotFoundException("No se encontro al usuario con nombre: $username")}
        return usuario
    }

    fun comprobarUser(user: Usuario): Boolean{
        return if(user.password.isBlank() || user.username.isBlank()) {
            throw BadRequestException("Los campos nombre y password son obligatorios")
        }else if (user.roles != "ADMIN" && user.roles != "USER" || user.roles.isNullOrEmpty()) {
            throw BadRequestException("El campo rol es obligatorio (solo pueden ser ADMIN o USER)")
        }else{ true }
    }
}