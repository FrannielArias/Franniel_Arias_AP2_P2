package edu.ucne.franniel_arias_ap2_p2.presentacion

import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos

interface GastosUiEvent {
    object ShowBottonSheet : GastosUiEvent
    object HideBottonSheet : GastosUiEvent
    data object Load : GastosUiEvent
    data class Crear(val gastos: Gastos) : GastosUiEvent
    data class GetGasto(val id: Int) : GastosUiEvent
    data class FechaChange(val value: String): GastosUiEvent
    data class SuplidorChange(val value: String): GastosUiEvent
    data class NcfChange(val value: String): GastosUiEvent
    data class ItbisChange(val value: String): GastosUiEvent
    data class MontoChange(val value: String): GastosUiEvent
}