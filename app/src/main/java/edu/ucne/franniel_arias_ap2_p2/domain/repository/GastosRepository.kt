package edu.ucne.franniel_arias_ap2_p2.domain.repository

import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import kotlinx.coroutines.flow.Flow

interface GastosRepository {
    fun getGastos(): Flow<Resource<List<Gastos>>>

    fun getGastos(id: Int): Flow<Resource<Gastos>>

    suspend fun saveGastos(gastos: Gastos)

    suspend fun updateGastos(gastos: Gastos)

    suspend fun deleteGastos(id: Int)
}
