package com.example.planificador.model

import jakarta.persistence.*

@Entity
@Table(name = "tipos_gasto")
data class TipoGasto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @Column(nullable = false)
    var name : String,

    @OneToMany(mappedBy = "tipo", cascade = [CascadeType.ALL])
    val gastosDiarios: List<Gasto> = emptyList()
)
