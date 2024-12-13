package com.example.planificador.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "gastos_diarios")
data class Gasto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "tipo_gasto_id", nullable = false)
    var tipo: TipoGasto,

    @Column(nullable = false)
    var cantidad: Double,

    @Column(nullable = false)
    var fecha: LocalDateTime,

    @Column
    var comentario: String? = null
)
