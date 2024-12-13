package com.example.planificador.error.exception

class TipoNotFoundException(message: String) : Exception("Not Found Exception (404). $message") {
}