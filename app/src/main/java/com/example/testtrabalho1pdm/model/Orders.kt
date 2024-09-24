package com.example.testtrabalho1pdm.model

data class Orders(
    val id: Int,
    val dataPedido: String,
    val valorTot: Double,
    val status: String,
    val observacao: String
)
