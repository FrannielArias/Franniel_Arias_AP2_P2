package edu.ucne.franniel_arias_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class GastosDto(
    @Json(name = "gastosId")
    val gastosId: Int?,

    @Json(name = "fecha")
    val fecha: String,

    @Json(name = "suplidor")
    val suplidor: String,

    @Json(name = "ncf")
    val ncf: String,

    @Json(name = "itbis")
    val itbis: Double,

    @Json(name = "monto")
    val monto: Double
)