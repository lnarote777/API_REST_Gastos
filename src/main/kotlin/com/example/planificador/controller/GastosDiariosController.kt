package com.example.planificador.controller

import com.example.planificador.error.exception.BadRequestException
import com.example.planificador.model.Gasto
import com.example.planificador.service.GastoDiarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller("/gastos_diarios")
class GastosDiariosController {

    @Autowired
    private lateinit var gastoService: GastoDiarioService

    @PostMapping("/nuevo_gasto")
    fun nuevoGasto(
        @RequestBody newGasto: Gasto?
    ):ResponseEntity<Gasto>{
        if (newGasto == null){
            throw BadRequestException("No se puede añadir un gasto vacío.")
        }

        val gasto = gastoService.newGastoDiario(newGasto)

        return ResponseEntity(gasto, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getGasto(
        @PathVariable id: String
    ): ResponseEntity<Gasto>{
        val gasto = gastoService.getGasto(id)
        return ResponseEntity(gasto, HttpStatus.OK)
    }

    @GetMapping("/")
    fun getAll(): ResponseEntity<List<Gasto>>{
        val list = gastoService.getAll()
        return ResponseEntity(list, HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(
        @PathVariable id: String
    ): ResponseEntity<Gasto>{
        val gasto = gastoService.delete(id)
        return ResponseEntity(gasto, HttpStatus.OK)
    }

    @PutMapping("/update")
    fun update(
        @RequestBody updateGasto: Gasto?
    ): ResponseEntity<Gasto>{
        if (updateGasto == null){
            throw BadRequestException("Escriba el gasto con los datos a modificar.")
        }

        val gasto = gastoService.update(updateGasto)
        return ResponseEntity(gasto, HttpStatus.OK)
    }
}