package edu.ucne.franniel_arias_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class GastosDto(
    @Json(name = "GastosId")
    val GastosId: Int?,

    @Json(name = "fecha")
    val Fecha: String,

    @Json(name = "suplidor")
    val suplidor: String,

    @Json(name = "ncf")
    val Nfc: String,

    @Json(name = "itbis")
    val Itbis: Int?,

    @Json(name = "monto")
    val Monto: Int?
)

