package edu.ucne.franniel_arias_ap2_p2.presentacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import edu.ucne.franniel_arias_ap2_p2.domain.useCase.GetGastoUseCase
import edu.ucne.franniel_arias_ap2_p2.domain.useCase.GetGastosUseCase
import edu.ucne.franniel_arias_ap2_p2.domain.useCase.SaveGastosUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastosViewModel @Inject constructor(
    private val getGastosUseCase: GetGastosUseCase,
    private val getGastoUseCase: GetGastoUseCase,
    private val saveGastosUseCase: SaveGastosUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GastosUiState(isLoading = true))
    val state: StateFlow<GastosUiState> = _state.asStateFlow()

    private var gastosJob: Job? = null

    init {
        obtenerGastos()
    }

    private fun obtenerGastos() {
        gastosJob?.cancel()

        gastosJob = viewModelScope.launch {
            getGastosUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                error = null,
                                message = null
                            )
                        }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                ListaGastos = result,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun obtenerGasto(id: Int) {
        viewModelScope.launch {
            getGastoUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        val gastos = result.data?.firstOrNull()
                        gastos?.let { gasto ->
                            _state.update { state ->
                                state.copy(
                                    gastosId = gasto.gastosId ?: 0,
                                    fecha = gasto.fecha,
                                    suplidor = gasto.suplidor,
                                    ncf = gasto.ncf,
                                    itbis = gasto.itbis.toString(),
                                    monto = gasto.monto.toString(),
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(error = result.message, isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: GastosUiEvent) {
        when (event) {
            is GastosUiEvent.Crear -> crearGasto(event.gastos)
            is GastosUiEvent.GetGasto -> obtenerGasto(event.id)
            is GastosUiEvent.Load -> obtenerGastos()

            is GastosUiEvent.ShowBottonSheet -> {
                _state.update {
                    it.copy(
                        isSheetVisible = true,
                        isLoading = false
                    )
                }
            }

            is GastosUiEvent.HideBottonSheet -> {
                _state.update {
                    it.copy(
                        isSheetVisible = false,
                        isLoading = false,
                        gastosId = 0,
                        fecha = "",
                        suplidor = "",
                        ncf = "",
                        itbis = "",
                        monto = ""
                    )
                }
            }

            is GastosUiEvent.FechaChange -> {
                _state.update { it.copy(fecha = event.value) }
            }

            is GastosUiEvent.SuplidorChange -> {
                _state.update { it.copy(suplidor = event.value) }
            }

            is GastosUiEvent.NcfChange -> {
                _state.update { it.copy(ncf = event.value) }
            }

            is GastosUiEvent.ItbisChange -> {
                _state.update { it.copy(itbis = event.value) }
            }

            is GastosUiEvent.MontoChange -> {
                _state.update { it.copy(monto = event.value) }
            }
        }
    }

    private fun crearGasto(gastos: Gastos) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                saveGastosUseCase(gastos)

                _state.update {
                    it.copy(
                        message = "Gasto creado correctamente",
                        isSheetVisible = false,
                        gastosId = 0,
                        fecha = "",
                        suplidor = "",
                        ncf = "",
                        itbis = "",
                        monto = "",
                        error = null,
                        isLoading = false
                    )
                }
                obtenerGastos()

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Error al guardar",
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gastosJob?.cancel()
    }
}