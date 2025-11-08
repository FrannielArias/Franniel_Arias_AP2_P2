package edu.ucne.franniel_arias_ap2_p2.domain.repository

import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import kotlinx.coroutines.flow.Flow

interface GastosRepository {
    fun getGastos(): Flow<Resource<List<Gastos>>>

    fun getGasto(id: Int): Flow<Resource<List<Gastos>>>

    suspend fun saveGastos(gastos: Gastos): Resource<Unit>

    suspend fun updateGastos(gastos: Gastos)

    suspend fun deleteGastos(id: Int)
}
