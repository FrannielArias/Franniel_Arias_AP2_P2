package edu.ucne.franniel_arias_ap2_p2.domain.useCase

import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import javax.inject.Inject


class DeleteGastosUseCase @Inject constructor(
    private val repository: GastosRepository
){
    suspend operator fun invoke(id: Int) {
        repository.deleteGastos(id)
    }
}