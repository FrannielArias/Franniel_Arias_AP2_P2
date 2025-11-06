package edu.ucne.franniel_arias_ap2_p2.domain.useCase

import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetGastoUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Gastos>> {
        return repository.getGastos(id)
    }
}