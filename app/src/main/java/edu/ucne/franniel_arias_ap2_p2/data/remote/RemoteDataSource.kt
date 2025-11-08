package edu.ucne.franniel_arias_ap2_p2.data.remote

import edu.ucne.franniel_arias_ap2_p2.data.remote.dto.GastosDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gastosApi: GastosApi
) {
    suspend fun getGastos(): List<GastosDto> {
        return gastosApi.getGastos()
    }

    suspend fun getGasto(id: Int): List<GastosDto> {
        return gastosApi.getGasto(id)
    }

    suspend fun saveGastos(gastosDto: GastosDto) {
        return gastosApi.saveGastos(gastosDto)
    }

    suspend fun updateGastos(gastosDto: GastosDto) {
        return gastosApi.updateGastos(gastosDto)
    }

    suspend fun deleteGastos(id: Int) {
        return gastosApi.deleteGastos(id)
    }
}