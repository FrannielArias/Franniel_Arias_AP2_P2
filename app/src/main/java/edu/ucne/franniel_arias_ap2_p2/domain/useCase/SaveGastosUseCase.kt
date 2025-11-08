package edu.ucne.franniel_arias_ap2_p2.domain.useCase

import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import java.lang.Exception
import javax.inject.Inject

class SaveGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    suspend operator fun invoke(gastos: Gastos){
        repository.saveGastos(gastos)
    }
}