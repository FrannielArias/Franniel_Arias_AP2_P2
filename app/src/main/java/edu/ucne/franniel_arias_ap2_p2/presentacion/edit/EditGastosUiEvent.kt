package edu.ucne.franniel_arias_ap2_p2.presentacion.edit


sealed interface EditGastosUiEvent {
    data class GastosIdChanged(val gastoId: String): EditGastosUiEvent
    data class SuplidorChanged(val suplidor:String): EditGastosUiEvent


    data object  Post: EditGastosUiEvent
    data object Nuevo: EditGastosUiEvent

    data object LimpiarErrorMessageSuplidor: EditGastosUiEvent

    data class Get(val id:Int): EditGastosUiEvent

    data object ResetSuccessMessage: EditGastosUiEvent
}