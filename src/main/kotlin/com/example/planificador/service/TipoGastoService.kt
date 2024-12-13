package com.example.planificador.service

import com.example.planificador.error.exception.BadRequestException
import com.example.planificador.error.exception.TipoNotFoundException
import com.example.planificador.error.exception.UserNotFoundException
import com.example.planificador.model.TipoGasto
import com.example.planificador.repository.TipoGastoReppository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TipoGastoService {

    @Autowired
    private lateinit var tipoGastoReppository: TipoGastoReppository

    fun newTipo(newTipo: TipoGasto): TipoGasto {

        val tipo = tipoGastoReppository.findById(newTipo.id!!)

        if (tipo.isPresent) {
            throw BadRequestException("El tipo de gasto ya existe.")
        }

        if (newTipo.name.isBlank()){
            throw BadRequestException("El nombre del tipo de gasto no puede estar vacío.")
        }

        tipoGastoReppository.save(newTipo)

        return newTipo

    }

    fun getTipoGasto(nameTipo: String): TipoGasto {
        val tipo = tipoGastoReppository.findByName(nameTipo).orElseThrow{ UserNotFoundException("No se encontró ningún tipo de gasto con nombre: $nameTipo") }
        return tipo
    }

    fun getAll():List<TipoGasto>{
        return tipoGastoReppository.findAll()
    }

    fun update(updateTipo: TipoGasto): TipoGasto {
        val tipo = updateTipo.id?.let{tipoGastoReppository.findById(it).orElseThrow{TipoNotFoundException("No se encontro ningun tipo con el id: ${updateTipo.id}")}}

        if (tipo != null){
            tipo.name = updateTipo.name
            return tipoGastoReppository.save(tipo)
        }
        throw TipoNotFoundException("No se encontró ningún tipo.")
    }

    fun delete(nameTipo: String): TipoGasto {
        val tipo = tipoGastoReppository.findByName(nameTipo).orElseThrow{ UserNotFoundException("No se encontró ningún tipo de gasto con nombre: $nameTipo") }

        if (tipo.gastosDiarios.isNotEmpty()){
            throw BadRequestException("No se puede eliminar un tipo de gasto asociado a gastos.")
        }

        tipoGastoReppository.delete(tipo)
        return tipo
    }
}