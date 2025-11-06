package edu.ucne.franniel_arias_ap2_p2.presentacion.edit

data class EditGastosUiState(
    val gastosId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Int,
    val monto: Int,
    
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val successMessage: String? = null,

    val suplidorErrorMessage: String? = null
)
