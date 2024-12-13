package com.example.planificador.error.exception

class GastoNotFoundException(message: String) : Exception("Not Found Exception (404). $message") {
}