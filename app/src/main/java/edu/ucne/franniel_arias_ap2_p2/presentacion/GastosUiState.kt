package edu.ucne.franniel_arias_ap2_p2.presentacion

import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos

data class GastosUiState(
    val gastosId: Int = 0,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val isSheetVisible: Boolean = false,
    val ListaGastos: Resource<List<Gastos>> = Resource.Loading()
)
