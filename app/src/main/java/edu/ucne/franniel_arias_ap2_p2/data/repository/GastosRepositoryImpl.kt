package edu.ucne.franniel_arias_ap2_p2.data.repository

import android.util.Log
import edu.ucne.franniel_arias_ap2_p2.data.mappers.toDomain
import edu.ucne.franniel_arias_ap2_p2.data.mappers.toDto
import edu.ucne.franniel_arias_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class GastosRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : GastosRepository {

    override fun getGastos(): Flow<Resource<List<Gastos>>> = flow {
        try {
            emit(Resource.Loading())
            val gastos = remoteDataSource.getGastos().map { it.toDomain() }
            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de Internet ${e.message}"))
            Log.e("GastosRepositoryImpl", "getGastos: ${e.message}")
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
            Log.e("GastosRepositoryImpl", "getPrioridades: ${e.message}")
        }
    }

    override fun getGastos(id: Int): Flow<Resource<Gastos>> = flow {
        try {
            emit(Resource.Loading())
            val dto = remoteDataSource.getGastos(id).firstOrNull()
            if (dto != null) {
                emit(Resource.Success(dto.toDomain()))
            } else {
                emit(Resource.Error("Gasto no encontrada"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Error de Internet ${e.message}"))
            Log.e("GastoRepositoryImpl", "getGasto(${id}): ${e.message}")
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido ${e.message}"))
            Log.e("GastosRepositoryImpl", "getGasto(${id}): ${e.message}")
        }
    }

    override suspend fun saveGastos(gastos: Gastos) =
        remoteDataSource.saveGastos(gastos.toDto())

    override suspend fun updateGastos(gastos: Gastos) =
        remoteDataSource.updateGastos(gastos.toDto())

    override suspend fun deleteGastos(id: Int) =
        remoteDataSource.deleteGastos(id)
}