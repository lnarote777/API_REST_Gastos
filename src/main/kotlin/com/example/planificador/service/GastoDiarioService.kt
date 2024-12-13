package com.example.planificador.service

import com.example.planificador.error.exception.BadRequestException
import com.example.planificador.error.exception.GastoNotFoundException
import com.example.planificador.model.Gasto
import com.example.planificador.repository.GastoDiarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class GastoDiarioService {
    @Autowired
    private lateinit var gastoDiarioRepository: GastoDiarioRepository

    fun newGastoDiario(newGasto: Gasto):Gasto{
        if (comprobarDatos(newGasto)){
            return gastoDiarioRepository.save(newGasto)
        }
        throw BadRequestException("")

    }

    fun update(gastoUpdate: Gasto): Gasto{
        if (comprobarDatos(gastoUpdate)){
            val gasto = gastoUpdate.id ?.let { gastoDiarioRepository.findById(it).orElseThrow { GastoNotFoundException("No se encontró ningún gasto con el id: $it") } }
            if (gasto != null){
                gasto.tipo = gastoUpdate.tipo
                gasto.fecha = gastoUpdate.fecha
                gasto.cantidad = gastoUpdate.cantidad
                gasto.name = gastoUpdate.name
                gasto.comentario = gastoUpdate.comentario
                return gastoDiarioRepository.save(gasto)
            }
            throw GastoNotFoundException("No se encontró ningún gasto con el id: ${gastoUpdate.id}")
        }
        throw BadRequestException("")
    }

    fun delete(id: String):Gasto{
        try {
            val idL = id.toLong()
            val  gasto = gastoDiarioRepository.findById(idL).orElseThrow()
            if (gasto != null) {
                gastoDiarioRepository.delete(gasto)
                return gasto
            }else{
                throw GastoNotFoundException("El id debe ser un numero entero.")
            }
        }catch (e: Exception){
            throw NumberFormatException("El id debe ser un numero entero.")
        }
    }

    fun getAll():List<Gasto>{
        return gastoDiarioRepository.findAll()
    }

    fun getGasto(id:String):Gasto{
        try {
            val idL = id.toLong()
            val  gasto = gastoDiarioRepository.findById(idL).orElseThrow()
            if (gasto != null) {
                return gasto
            }else{
                throw GastoNotFoundException("El id debe ser un numero entero.")
            }
        }catch (e: Exception){
            throw NumberFormatException("El id debe ser un numero entero.")
        }
    }


    fun comprobarDatos(gasto: Gasto): Boolean {
        if (gasto.name.isBlank() || gasto.tipo.name.isBlank()){
            throw BadRequestException("Nombre o tipo inválidos.")
        }else if (gasto.fecha.isAfter(LocalDateTime.now())){
            throw BadRequestException("La fecha no puede ser posterior a la fecha actual")
        }else if (gasto.cantidad < 0.0){
            throw BadRequestException("La cantidad debe se un número positivo")
        }

        return true
    }

}