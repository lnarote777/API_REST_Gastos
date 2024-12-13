package com.example.planificador.repository

import com.example.planificador.model.TipoGasto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TipoGastoReppository: JpaRepository<TipoGasto, Long> {
    fun findByName(name: String): Optional<TipoGasto>
}