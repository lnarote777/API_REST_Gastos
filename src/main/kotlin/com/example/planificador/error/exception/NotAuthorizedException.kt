package com.example.planificador.error.exception

class NotAuthorizedException(message: String) : Exception("Not Authorized Exception (401). $message") {
}