package edu.ucne.franniel_arias_ap2_p2.data.remote

import android.util.Log
import edu.ucne.franniel_arias_ap2_p2.data.remote.dto.GastosDto
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val gastosApi: GastosApi
) {
    suspend fun getGastos(): List<GastosDto> {
        try {
            val response = gastosApi.getGastos()
            Log.d("RemoteDataSource", "getGastos, Obtuvo : ${response.size} registros.")
            return response
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getGastos(id: Int): List<GastosDto> {
        try {
            val response = gastosApi.getGasto(id)
            Log.d(
                "RemoteDataSource",
                "getPrioridad - Respuesta exitosa: ${response.size} empleados para id=$id"
            )
            return response
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Error en getGastos(id=$id): ${e.message}", e)
            throw e
        }
    }

    suspend fun updateGastos(gastosDto: GastosDto) {
        try {
            val response = gastosApi.updateGastos(gastosDto)
            Log.d("RemoteDataSource", "update - Respuesta: ${response.code()}")
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Error en update(): ${e.message}", e)
            throw e
        }
    }

    suspend fun saveGastos(gastosDto: GastosDto) {
        try {
            val response = gastosApi.saveGastos(gastosDto)
            Log.d("RemoteDataSource", "savegasto - Respuesta: ${response.code()}")
            if (response.isSuccessful) {
                Log.d("RemoteDataSource", "gasto guardado exitosamente")
            } else {
                Log.e("RemoteDataSource", "Error al guardar gasto - Codigo: ${response.code()}, Mensaje: ${response.message()}")
            }
        }catch (e: Exception) {
            Log.e("RemoteDataSource", "Error en save(): ${e.message}", e)
            throw e
        }
    }

    suspend fun deleteGastos(id: Int) {
        try {
            val response = gastosApi.deleteGastos(id)
            Log.d("RemoteDataSource", "delete - Respuesta: ${response.code()}")
        }catch (e: Exception) {
            Log.e("RemoteDataSource", "Error en delete(id=$id): ${e.message}", e)
            throw e
        }
    }
}