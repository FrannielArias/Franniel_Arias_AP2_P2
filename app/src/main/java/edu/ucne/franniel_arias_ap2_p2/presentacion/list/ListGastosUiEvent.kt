package edu.ucne.franniel_arias_ap2_p2.presentacion.list

sealed interface ListGastosUiEvent {
    data object GetAll : ListGastosUiEvent
}