package com.example.appcomida.dataclass

data class alimento(
    val idAlimento: Int,
    val nomeAlimento: String,
    val calorias: Double?,
    val especificacoes: String?,
    val validade: String
)
