package edu.ucne.franniel_arias_ap2_p2.data.repository

import retrofit2.HttpException
import edu.ucne.franniel_arias_ap2_p2.data.mappers.toDomain
import edu.ucne.franniel_arias_ap2_p2.data.mappers.toDto
import edu.ucne.franniel_arias_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class GastosRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : GastosRepository {

    override fun getGasto(id: Int): Flow<Resource<List<Gastos>>> = flow {
        try {
            emit(Resource.Loading<List<Gastos>>())
            val gastosDto = remoteDataSource.getGasto(id)
            val gastos = gastosDto.map { it.toDomain() }
            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.localizedMessage}"))
        }
    }

    override suspend fun saveGastos(gastos: Gastos): Resource<Unit> {
        return try {
            val dto = gastos.toDto()
            remoteDataSource.saveGastos(dto)
            Resource.Success(Unit)
        } catch (e: HttpException){
            Resource.Error("Error de servidor: ${e.message}")
        } catch (e: Exception){
            Resource.Error("Error desconocido: ${e.localizedMessage}")
        }
    }

    override suspend fun updateGastos(gastos: Gastos) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGastos(id: Int) {
        TODO("Not yet implemented")
    }


    override fun getGastos(): Flow<Resource<List<Gastos>>> = flow {
        try {
            emit(Resource.Loading())
            val gastosDto = remoteDataSource.getGastos()
            val gastos = gastosDto.map { it.toDomain() }
            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de servidor: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.localizedMessage}"))
        }
    }
}