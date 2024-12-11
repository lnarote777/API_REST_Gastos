package com.example.planificador.model

import jakarta.persistence.*

@Entity
@Table(name = "tipos_gasto")
data class TipoGasto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,

    @Column(nullable = false)
    val name : String,

    @OneToMany(mappedBy = "tipo", cascade = [CascadeType.ALL])
    val gastosDiarios: List<Gasto> = emptyList()
)
