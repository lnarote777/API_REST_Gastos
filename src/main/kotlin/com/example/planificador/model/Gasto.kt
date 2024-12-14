package com.example.planificador.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "gastos_diarios")
data class Gasto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "tipo_gasto_id", nullable = false)
    @JsonManagedReference
    var tipo: TipoGasto,

    @Column(nullable = false)
    var cantidad: Double,

    @Column(nullable = false)
    var fecha: LocalDateTime,

    @Column
    var comentario: String? = null
)
