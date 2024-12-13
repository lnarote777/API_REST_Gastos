package com.example.planificador.repository

import com.example.planificador.model.Gasto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GastoDiarioRepository: JpaRepository<Gasto, Long> {
}