package edu.ucne.franniel_arias_ap2_p2.presentacion.list

import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos

data class ListGastosUiState(
    val listPrioridades: List<Gastos> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
