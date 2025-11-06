package edu.ucne.franniel_arias_ap2_p2.domain.model

data class Gastos(
    val gastosId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Int,
    val monto: Int
)
