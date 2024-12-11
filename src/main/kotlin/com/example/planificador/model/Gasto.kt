package com.example.planificador.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "gastos_diarios")
data class Gasto(
    @Id
    @Column(nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "tipo_gasto_id", nullable = false)
    val tipo: TipoGasto,

    @Column(nullable = false)
    val cantidad: Double,

    @Column(nullable = false)
    val fecha: Date,

    @Column
    val comentario: String? = null
)
