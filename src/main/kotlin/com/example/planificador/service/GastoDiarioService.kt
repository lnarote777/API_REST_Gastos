package com.example.planificador.service

import com.example.planificador.error.exception.BadRequestException
import com.example.planificador.error.exception.GastoNotFoundException
import com.example.planificador.error.exception.TipoNotFoundException
import com.example.planificador.model.Gasto
import com.example.planificador.repository.GastoDiarioRepository
import com.example.planificador.repository.TipoGastoReppository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class GastoDiarioService {
    @Autowired
    private lateinit var gastoDiarioRepository: GastoDiarioRepository

    @Autowired
    private lateinit var tipoGastoService: TipoGastoService

    fun newGastoDiario(newGasto: Gasto):Gasto{
        comprobarDatos(newGasto)

        return gastoDiarioRepository.save(newGasto)
    }

    fun update(gastoUpdate: Gasto): Gasto{

        comprobarDatos(gastoUpdate)

        val gasto = gastoUpdate.id ?.let { gastoDiarioRepository.findById(it).orElseThrow { GastoNotFoundException("No se encontró ningún gasto con el id: $it") } }

        if (gasto == null) {
            throw GastoNotFoundException("No se encontró ningún gasto con el id: ${gastoUpdate.id}")
        }

        gasto.tipo = gastoUpdate.tipo
        gasto.fecha = gastoUpdate.fecha
        gasto.cantidad = gastoUpdate.cantidad
        gasto.name = gastoUpdate.name
        gasto.comentario = gastoUpdate.comentario

        return gastoDiarioRepository.save(gasto)



    }

    fun delete(id: String):Gasto{
        val idL = id.toInt()
        val  gasto = gastoDiarioRepository.findById(idL)
            .orElseThrow {GastoNotFoundException("No se encontró ningún gasto con el id: $id")}
        gastoDiarioRepository.delete(gasto)
        return gasto
    }

    fun getAll():List<Gasto>{
        return gastoDiarioRepository.findAll()
    }

    fun getGasto(id:String):Gasto{
        val idL = id.toInt()
        val  gasto = gastoDiarioRepository.findById(idL)
            .orElseThrow {GastoNotFoundException("No se encontró ningún gasto con el id: $id")}
        return gasto
    }


    fun comprobarDatos(gasto: Gasto): Boolean {
        val tipos = tipoGastoService.getAll()
        val tipo = tipoGastoService.getTipoGasto(gasto.tipo.name)
        if (gasto.name.isBlank()){
            throw BadRequestException("Nombre o tipo inválidos.")
        }else if (gasto.fecha?.isAfter(LocalDateTime.now()) == true || gasto.fecha == null){
            throw BadRequestException("La fecha no puede ser posterior a la fecha actual")
        }else if (gasto.cantidad < 0.0){
            throw BadRequestException("La cantidad debe se un número positivo")
        }else if (!tipos.any { it == tipo }){
            throw TipoNotFoundException("No se encontro el tipo de gasto.")
        }

        return true
    }

}