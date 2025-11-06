package edu.ucne.franniel_arias_ap2_p2.data.mappers

import edu.ucne.franniel_arias_ap2_p2.data.remote.dto.GastosDto
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos

data class GastosMappers(
    val isValid: Boolean,
    val error: String
)

fun GastosDto.toDomain() = Gastos(
    gastosId = this.GastosId ?: 0,
    fecha = this.Fecha,
    suplidor = this.suplidor,
    ncf = this.Nfc,
    itbis = this.Itbis ?: 0,
    monto = this.Monto ?: 0
)

fun Gastos.toDto() = GastosDto(
    GastosId = gastosId,
    Fecha = fecha,
    suplidor = suplidor,
    Nfc = ncf,
    Itbis = itbis,
    Monto = monto
)
