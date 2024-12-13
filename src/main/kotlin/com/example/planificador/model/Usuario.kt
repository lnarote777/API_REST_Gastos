package com.example.planificador.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    val username: String,
    var password: String,

    @Column(nullable = false)
    val roles: String? = null,
)
