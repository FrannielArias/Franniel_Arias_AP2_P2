package edu.ucne.franniel_arias_ap2_p2.data.repository

import edu.ucne.franniel_arias_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.data.remote.dto.GastosDto
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class GastosRepositoty @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val gastosDto: GastosDto
){
    fun getGastos(gastosId: Int): Flow<Resource<List<GastosDto>>> = flow{
        try{
            emit(Resource.Loading())
            val gasto = remoteDataSource.getGastos(gastosId)
            emit(Resource.Success(gasto))
        }catch (e: HttpException){
            emit(Resource.Error("Error de internet: ${e.message}"))
        }catch (e: Exception){
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    suspend fun saveGastos(gastos: Gastos) = remoteDataSource.saveGastos(gastosDto)
    suspend fun deleteGastos(id: Int) = remoteDataSource.deleteGastos(id)
    suspend fun editGastos(gastosDto: GastosDto) = remoteDataSource.updateGastos(gastosDto)

    fun getGastos(): Flow<Resource<List<GastosDto>>> = flow{
        try{
            emit(Resource.Loading())
            val gasto = remoteDataSource.getGastos()
            emit(Resource.Success(gasto))
        }catch (e: HttpException){
            emit(Resource.Error("Error de internet: ${e.message}"))
        }catch (e: Exception){
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }
}