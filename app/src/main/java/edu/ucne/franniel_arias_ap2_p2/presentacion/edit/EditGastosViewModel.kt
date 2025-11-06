package edu.ucne.franniel_arias_ap2_p2.presentacion.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.useCase.GetGastosUseCase
import edu.ucne.franniel_arias_ap2_p2.domain.useCase.SaveGastosPrioridadesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditGastosViewModel @Inject constructor(
    private val getUseCase: GetGastosUseCase,
    private val saveUseCase: SaveGastosPrioridadesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditGastosUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: EditGastosViewModel) {
        when (event) {
            is EditGastosViewModel.GastosIdChanged -> gastosIdChanged(event.gastosId)
            is EditGastosUiEvent.GastosIdChanged -> gastosChanged(event.suplidor)
            is EditGastosUiEvent.Get -> findPrioridad(event.id)
            EditGastosUiEvent.LimpiarErrorMessageSuplidor -> limpiarErrorMessageSuplidor()
            EditGastosUiEvent.Nuevo -> nuevo()
            EditGastosUiEvent.Post -> addPrioridad()
            EditGastosUiEvent.ResetSuccessMessage -> _uiState.update {
                it.copy(
                    isSuccess = false,
                    successMessage = null
                )
            }
        }
    }

    private fun gastosChanged(id: String) {
        _uiState.update { it.copy(gastosId = id) }
    }

    private fun suplidorChanged(suplidor: String) {
        _uiState.update { it.copy(suplidor = suplidor) }
    }

    private fun limpiarErrorMessageSuplidor() {
        _uiState.update { it.copy(suplidorErrorMessage = null) }
    }

    private fun nuevo() {
        _uiState.update {
            it.copy(
                gastosId = null,
                fecha = "",
                suplidor = "",
                ncf = "",
                itbis = null,
                monto = null,

                errorMessage = null,
                suplidorErrorMessage = null
            )
        }
    }

    private fun addGasto() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorMessage = null,
                    suplidorErrorMessage = null,
                    isLoading = true
                )
            }

            val suplidor = _uiState.value.suplidor.trim()

            val gastos = Gastos(
                gastosId = 0,
                fecha = fecha,
                suplidor = suplidor,
                ncf = ncf,
                itbis = itbis,
                monto = monto
            )

            val result = saveUseCase(gastos)

            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSuccess = true,
                            successMessage = "Guardado Correctamente.",
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                },
                onFailure = { exception ->
                    val errorMessage = exception.message ?: "Error Desconocido"
                    when{
                        errorMessage.contains("suplidor", ignoreCase = true) ->{
                            _uiState.update { it.copy(suplidorErrorMessage = errorMessage, isLoading = false) }
                        }
                        else ->{
                            _uiState.update { it.copy(errorMessage = errorMessage, isLoading = false) }
                        }
                    }

                }
            )

        }
    }

    private fun findGastos(id:Int) {
        viewModelScope.launch {
            if(id>0){
                getUseCase(id).collect { resource->
                    when(resource){
                        is Resource.Success ->{
                            val gastos = resource.data
                            _uiState.update {
                                it.copy(
                                    gastosId =  gastos?.gastosId?.toString() ?: "",
                                    fecha = gastos?.fecha ?: "",
                                    suplidor = gastos?.suplidor ?: "",
                                    ncf = gastos?.ncf?: "",
                                    itbis = gastos?.itbis?: "",
                                    monto = gastos?.monto?: "",

                                    isLoading = false
                                )
                            }
                        }
                        is Resource.Error->{
                            _uiState.update { it.copy(errorMessage = resource.message, isLoading = false) }
                        }
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
        }
    }


}