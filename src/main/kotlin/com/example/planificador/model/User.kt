package com.example.planificador.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    val name: String,
    val password: String,

    @Column(nullable = false)
    val role: String,
)
