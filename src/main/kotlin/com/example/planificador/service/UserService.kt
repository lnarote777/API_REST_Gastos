package com.example.planificador.service

import com.example.planificador.error.exception.UserNotFoundException
import com.example.planificador.model.Usuario
import com.example.planificador.repository.UsuarioRepository
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.GrantedAuthority
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
            .orElseThrow()

        val roles: List<SimpleGrantedAuthority> = usuario.roles
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

        if (user.isPresent()) {
            throw BadRequestException("Usuario existente")
        }

        if(newUsuario.password.isBlank() || newUsuario.username.isBlank()) {
            throw BadRequestException("Los campos nombre y password son obligatorios")
        }else if (newUsuario.roles != "ADMIN" && newUsuario.roles != "USER") {
            throw BadRequestException("El campo rol es obligatorio")
        }

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
        val usuario = updateUser.id?.let { userRespository.findById(it).orElseThrow{UserNotFoundException("No se encontró al usuario")}}

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
}