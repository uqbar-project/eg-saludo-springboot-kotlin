package org.uqbar.egsaludospringkotlin.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.annotation.processing.Generated

@RestController
class SaludoController {
    val saludador = Saludador()

    @GetMapping("/saludoDefault")
    @Operation(summary = "Devuelve un saludo por defecto")
    fun darSaludo() = this.saludador.buildSaludo()

    @GetMapping("/saludo/{persona}")
    @Operation(summary = "Devuelve un saludo personalizado, requiere la persona a saludar")
    fun darSaludoCustom(@PathVariable persona: String) =
        this.saludador.buildSaludoCustom("Hola $persona!")

    @PutMapping("/saludoDefault")
    @Operation(summary = "Actualiza el valor del nuevo saludo por defecto")
    fun actualizarSaludo(@RequestBody nuevoSaludo: String): String {
        this.saludador.cambiarSaludoDefault(nuevoSaludo)
        return "Se actualizó el saludo correctamente"
    }
}

class Saludador {
    companion object {
        var ultimoId = 1
        val DODAIN = "dodain"
    }

    private var saludoDefault = "Hola mundo!"

    fun buildSaludo() = buildSaludoCustom(this.saludoDefault)

    fun buildSaludoCustom(mensaje: String) = Saludo(ultimoId++, mensaje)

    fun cambiarSaludoDefault(nuevoSaludo: String) {
        if (nuevoSaludo == DODAIN) {
            throw BusinessException("No se puede saludar a $DODAIN")
        }
        this.saludoDefault = nuevoSaludo
    }
}

@Generated
data class Saludo(
    val id: Int,
    val mensaje: String,
) {
    val fechaCreacion: LocalDateTime = LocalDateTime.now()
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(message: String) : RuntimeException(message) {}
