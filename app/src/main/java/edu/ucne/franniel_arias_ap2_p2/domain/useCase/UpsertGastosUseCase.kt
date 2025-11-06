package edu.ucne.franniel_arias_ap2_p2.domain.useCase

import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import java.lang.Exception
import javax.inject.Inject

class UpdateGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    suspend operator fun invoke(gastos: Gastos): Result<Unit>{
        val validacion = validateSuplidor(gastos.suplidor)
        if(!validacion.isValid)
            return Result.failure(Exception(validacion.error))

        return runCatching { repository.updateGastos(gastos) }
    }
}