package com.example.planificador.error.exception

class UserNotFoundException(message: String) : Exception("Not Found Exception (404). $message") {
}