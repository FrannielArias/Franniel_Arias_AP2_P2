package edu.ucne.franniel_arias_ap2_p2.data.mappers

import edu.ucne.franniel_arias_ap2_p2.data.remote.dto.GastosDto
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos

data class GastosMappers(
    val isValid: Boolean,
    val error: String
)

fun GastosDto.toDomain() = Gastos(
    gastosId = gastosId,
    fecha = fecha,
    suplidor = suplidor,
    ncf = ncf,
    itbis = itbis,
    monto = monto
)

fun Gastos.toDto() = GastosDto(
    gastosId = gastosId,
    fecha = fecha,
    suplidor = suplidor,
    ncf = ncf,
    itbis = itbis,
    monto = monto
)
